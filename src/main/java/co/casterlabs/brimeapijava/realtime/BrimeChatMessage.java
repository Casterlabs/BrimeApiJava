package co.casterlabs.brimeapijava.realtime;

import com.google.gson.annotations.SerializedName;

import co.casterlabs.brimeapijava.types.BrimeUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BrimeChatMessage {
    @SerializedName("channelID")
    private String channelId;
    private String message;
    private BrimeUser sender;

}
