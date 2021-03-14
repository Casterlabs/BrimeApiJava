package co.casterlabs.brimeapijava.types;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BrimeChannel {
	@SerializedName("_id")
	private String id;
	@SerializedName("channel")
	private String channelName;
	private String title;
	private String category;

}
