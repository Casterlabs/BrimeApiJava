package co.casterlabs.brimeapijava.realtime;

import java.io.Closeable;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonObject;

import co.casterlabs.brimeapijava.BrimeApi;
import io.ably.lib.realtime.AblyRealtime;
import io.ably.lib.realtime.Channel;
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
    public BrimeRealtime(@NonNull String ablyToken, @NonNull String channelName) throws AblyException {
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

        this.ably.channels.get(channelName.toLowerCase() + "/alerts").subscribe((message) -> {
            if (this.listener != null) {
                switch (message.name) {
                    case "alert": {
                        JsonObject data = BrimeApi.GSON.fromJson((String) message.data, JsonObject.class);

                        this.listener.onFollow(data.get("follower").getAsString(), data.get("followerID").getAsString());
                        break;
                    }

                    default:
                        break;
                }
            }
        });

        Channel channel = this.ably.channels.get(channelName.toLowerCase());

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
                    case "greeting": {
                        BrimeChatMessage chat = BrimeApi.GSON.fromJson((String) message.data, BrimeChatMessage.class);

                        this.listener.onChat(chat);
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

    @Override
    public void close() {
        this.ably.close();
    }

}
