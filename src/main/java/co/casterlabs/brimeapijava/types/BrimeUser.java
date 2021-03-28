package co.casterlabs.brimeapijava.types;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BrimeUser {
    @SerializedName("_id")
    private String userId;

    private String username;

    private String displayname;

    private String avatar;

    private String color;

    private List<String> roles;

    private List<String> badges;

}
