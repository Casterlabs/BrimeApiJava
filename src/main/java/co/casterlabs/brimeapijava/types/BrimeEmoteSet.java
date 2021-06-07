package co.casterlabs.brimeapijava.types;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BrimeEmoteSet {
    @SerializedName("_id")
    private String emoteSetId;

    private String name;

    @Nullable
    @SerializedName("channelID")
    private String channelId;

    private List<BrimeEmote> emotes;

}
