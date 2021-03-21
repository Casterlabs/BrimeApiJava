package co.casterlabs.brimeapijava.realtime;

import java.io.Closeable;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;

import co.casterlabs.brimeapijava.BrimeApi;
import io.ably.lib.realtime.AblyRealtime;
import io.ably.lib.types.AblyException;
import io.ably.lib.types.ClientOptions;
import lombok.NonNull;
import lombok.Setter;

public class BrimeRealtimeChat implements Closeable {
    private AblyRealtime ably;

    private @Setter @Nullable BrimeChatListener listener;

    /**
     * Only use this if you've gotten permission.
     * 
     * Make sure to set {@link BrimeApi.ABLY_REALTIME_TOKEN}
     * 
     * @throws AblyException
     */
    @Deprecated
    public BrimeRealtimeChat(@NonNull String ablyToken, @NonNull String channelName) throws AblyException {
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

        this.ably.channels.get(channelName).subscribe((message) -> {
            if (this.listener != null) {
                switch (message.name) {
                    case "greeting": {
                        JsonObject data = BrimeApi.GSON.fromJson((String) message.data, JsonObject.class);

                        this.listener.onChat(data.get("username").getAsString(), data.get("color").getAsString(), data.get("message").getAsString());
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

    @Override
    public void close() {
        this.ably.close();
    }

}
