package co.casterlabs.brimeapijava.types;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BrimeCategory {
    @SerializedName("_id")
    private String categoryId;

    private List<String> genres;

    private String name;

    private String slug;

    private String summary;

    private String cover;

    private String type;

}
