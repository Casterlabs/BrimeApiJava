package co.casterlabs.brimeapijava.types;

import java.util.List;

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

    private String streamThumbnailUrl;

    private BrimeCategory category;

    private String title;

    private boolean isLive;

    private long publishTime;

    private List<BrimeStreamMeta> streams;

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
