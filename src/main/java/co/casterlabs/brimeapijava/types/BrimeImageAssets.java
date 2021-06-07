package co.casterlabs.brimeapijava.types;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BrimeImageAssets {
    @SerializedName("1x")
    private String smallImageLink;

    @SerializedName("2x")
    private String mediumImageLink;

    @SerializedName("3x")
    private String largeImageLink;

}
