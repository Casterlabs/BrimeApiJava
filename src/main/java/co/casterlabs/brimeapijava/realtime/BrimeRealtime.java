package co.casterlabs.brimeapijava.realtime;

import java.io.Closeable;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;

import co.casterlabs.brimeapijava.BrimeApi;
import io.ably.lib.realtime.AblyRealtime;
import io.ably.lib.realtime.Channel;
import io.ably.lib.realtime.ConnectionState;
import io.ably.lib.types.AblyException;
import io.ably.lib.types.ClientOptions;
import lombok.NonNull;
import lombok.Setter;

public class BrimeRealtime implements Closeable {
    private AblyRealtime ably;

    private @Setter @Nullable BrimeRealtimeListener listener;

    /**
     * Only use this if you've gotten permission.
     * 
     * @throws AblyException
     */
    @Deprecated
    public BrimeRealtime(@NonNull String ablyToken, @NonNull String channelId) throws AblyException {
        ClientOptions options = new ClientOptions(ablyToken);

        options.autoConnect = false;
        options.logLevel = Integer.MAX_VALUE;

        this.ably = new AblyRealtime(options);

        this.ably.connection.on((state) -> {
            if (this.listener != null) {
                switch (state.current) {
                    case connected: {
                        this.listener.onOpen();
                        break;
                    }

                    case closed:
                    case failed: {
                        this.listener.onClose();
                        break;
                    }

                    default:
                        break;
                }
            }
        });

        this.ably.channels.get(channelId + "/events").subscribe((message) -> {
            if (this.listener != null) {
                JsonObject data = BrimeApi.GSON.fromJson((String) message.data, JsonObject.class);

                switch (message.name) {
                    case "follow": {
                        this.listener.onFollow(data.get("follower").getAsString(), data.get("followerID").getAsString());
                        break;
                    }

                    case "subscribe": {
                        this.listener.onSub(data.get("subscriber").getAsString(), data.get("subscriberID").getAsString(), false);
                        break;
                    }

                    case "resubscribe": {
                        this.listener.onSub(data.get("subscriber").getAsString(), data.get("subscriberID").getAsString(), true);
                        break;
                    }

                    default:
                        break;
                }
            }
        });

        Channel channel = this.ably.channels.get(channelId + "/chat");

        channel.presence.subscribe((message) -> {
            if (this.listener != null) {
                switch (message.action) {
                    case absent:
                    case leave:
                        this.listener.onLeave(message.clientId);
                        break;

                    case enter:
                    case present:
                    case update:
                        this.listener.onJoin(message.clientId);
                        break;
                }
            }
        });

        channel.subscribe((message) -> {
            if (this.listener != null) {
                switch (message.name) {
                    case "chat": {
                        BrimeChatMessage chat = BrimeApi.GSON.fromJson((String) message.data, BrimeChatMessage.class);

                        this.listener.onChat(chat);
                        break;
                    }

                    case "delete": {
                        JsonObject json = BrimeApi.GSON.fromJson((String) message.data, JsonObject.class);

                        String messageId = json.get("messageID").getAsString();

                        this.listener.onChatDelete(messageId);
                        break;
                    }

                    default:
                        break;
                }
            }
        });
    }

    public void connect() {
        this.ably.connect();
    }

    public boolean isOpen() {
        return this.ably.connection.state == ConnectionState.connected;
    }

    @Override
    public void close() {
        this.ably.close();
    }

}
