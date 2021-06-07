package co.casterlabs.brimeapijava.types;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BrimeClip {
    @SerializedName("_id")
    private String clipId;

    private String url;

    private String clipName;

    private long clipDate;

    private String clipVideoUrl;

    private String clipThumbnailUrl;

    @SerializedName("channelID")
    private String channelId;

    @SerializedName("stream")
    private BrimeThinStreamData streamData;

    private long sectionStart;

    private long sectionEnd;

    private int upvotes;

}
