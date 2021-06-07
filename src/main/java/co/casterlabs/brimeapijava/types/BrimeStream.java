package co.casterlabs.brimeapijava.types;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BrimeStream {
    @SerializedName("_id")
    private String channelId;

    @SerializedName("channel")
    private String channelName;

    @Nullable
    private String streamThumbnailUrl;

    private BrimeCategory category;

    private String title;

    private boolean isLive;

    private long publishTime;

    @Nullable
    private List<BrimeStreamMeta> streams;

    @Nullable
    private BrimeUser broadcastingUser;

    @Getter
    @ToString
    public static class BrimeStreamMeta {
        private int bandwidth;

        private String resolution;

        @SerializedName("vcodec")
        private String videoCodec;

        @SerializedName("acodec")
        private String audioCodec;

        private String protocol;

        private boolean isSource;

    }

}
