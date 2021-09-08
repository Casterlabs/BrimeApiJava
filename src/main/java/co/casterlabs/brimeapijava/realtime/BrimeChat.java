package co.casterlabs.brimeapijava.realtime;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.jetbrains.annotations.Nullable;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.BrimeAuth;
import co.casterlabs.brimeapijava.requests.BrimeGetAccountRequest;
import co.casterlabs.brimeapijava.types.BrimeAccount;
import co.casterlabs.brimeapijava.types.BrimeChannel;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
public class BrimeChat implements Closeable {

    // Connection
    private MqttConnectOptions options = new MqttConnectOptions();
    private IMqttClient publisher;

    private String channel;
    private BrimeAuth auth;

    private @Setter @Nullable BrimeChatListener listener;

    public BrimeChat(@NonNull BrimeChannel channel, @NonNull BrimeAuth auth) throws IOException, ApiAuthException, ApiException {
        try {
            this.channel = channel.getChannel().getXid(); // This will be XID based soon.
            this.auth = auth;

            BrimeAccount account = new BrimeGetAccountRequest(this.auth)
                .send();

            String username = account.getXid();

            this.publisher = new MqttClient("ssl://chat-us.brime.tv:8084", UUID.randomUUID().toString(), new MemoryPersistence());

            this.options.setCleanSession(true);
            this.options.setConnectionTimeout(4000);
            this.options.setUserName(username);

            this.publisher.setCallback(new MqttCallback() {

                @Override
                public void connectionLost(Throwable cause) {
                    if (listener != null) {
                        listener.onClose(true);
                    }
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {}

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {}
            });

        } catch (MqttException e) {
            throw new IOException(e);
        }
    }

    public BrimeChat connect() throws IOException, ApiAuthException {
        if (this.isOpen()) {
            throw new IllegalStateException("You must close the connection before reconnecting.");
        } else {
            try {
                // Fresh auth.
                if (this.auth.isExpired()) {
                    this.auth.refresh();
                }
                this.options.setPassword(this.auth.getAccessToken().toCharArray());

                this.publisher.connect(this.options);

                this.publisher.subscribe(
                    "channel/chat/receive/" + this.channel,
                    (topic, message) -> {
                        if (this.listener != null) {
                            try {
                                String payload = new String(message.getPayload(), StandardCharsets.UTF_8);
                                JsonObject jsonPayload = BrimeApi.RSON.fromJson(payload, JsonObject.class);

                                // Loop through the thread and find the parent
                                JsonObject curr = jsonPayload;
                                while (!curr.get("reply").isJsonBoolean()) {
                                    curr = curr.getObject("reply");
                                }
                                curr.remove("reply");

                                BrimeChatMessage chatMessage = BrimeApi.RSON.fromJson(jsonPayload, BrimeChatMessage.class);

                                this.listener.onChat(chatMessage);
                            } catch (JsonParseException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                );

                if (this.listener != null) {
                    this.listener.onOpen();
                }

                return this;
            } catch (MqttException e) {
                throw new IOException(e);
            }
        }
    }

    public boolean isOpen() {
        return this.publisher.isConnected();
    }

    @Override
    public void close() throws IOException {
        try {
            this.publisher.close();
        } catch (MqttException e) {
            throw new IOException(e);
        } finally {
            if (this.listener != null) {
                this.listener.onClose(false);
            }
        }
    }

}
