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

    private BrimeCategory category;

    private String title;

    private boolean isLive;

    private String publishTime;

    private int bandwidth;

    private List<String> resolutions;

    @SerializedName("vcodec")
    private String videoCodec;

    @SerializedName("acodec")
    private String audioCodec;

    private String protocol;

}
