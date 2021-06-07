package co.casterlabs.brimeapijava.types;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BrimeVod {
    @SerializedName("_id")
    private String vodId;

    @SerializedName("channelID")
    private String channelId;

    private String vodVideoUrl;

    private String vodThumbnailUrl;

    @SerializedName("stream")
    private BrimeThinStreamData streamData;

    private String state;

    private long startDate;

    private long endDate;

    private long expiresAt;

}
