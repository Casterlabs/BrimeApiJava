package co.casterlabs.brimeapijava.types;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BrimeStream {
	@SerializedName("strm")
	private String streamer;

	@SerializedName("publish_time")
	private long publishTime;

	private int bandwidth;

	private String resolution;

	private String protocol;

	@SerializedName("vcodec")
	private String videoCodec;

	@SerializedName("acodec")
	private String audioCodec;

}
