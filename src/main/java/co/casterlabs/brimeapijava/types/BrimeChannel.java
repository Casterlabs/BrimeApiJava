package co.casterlabs.brimeapijava.types;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BrimeChannel {
    @SerializedName("_id")
    private String channelId;

    @SerializedName("channel")
    private String channelName;

    private String icon;

    private BrimeCategory category;

    private String title;

    private boolean isLive;

    private int followerCount;

    private int subscriberCount;

    private String description;

    private boolean isMonetized;

    private boolean vodsEnabled;

    private String offlineImageUrl;

    private List<String> owners;

    private List<String> moderators;

}
