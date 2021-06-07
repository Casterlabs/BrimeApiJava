package co.casterlabs.brimeapijava.types;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BrimeEmote {
    @SerializedName("_id")
    private String emoteId;

    private String name;

    @SerializedName("emoteSet")
    private String emoteSetId;

    private BrimeImageAssets images;

}
