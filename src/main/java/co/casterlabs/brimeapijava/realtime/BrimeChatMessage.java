package co.casterlabs.brimeapijava.realtime;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import co.casterlabs.brimeapijava.types.BrimeUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class BrimeChatMessage {
    private static final String IMAGE_URL_FORMAT = "https://content.brimecdn.com/brime/emote/%s/%s";

    @SerializedName("channelID")
    private String channelId;
    private String message;
    private BrimeUser sender;
    private Map<String, BrimeChatEmote> emotes = new HashMap<>();

    @Getter
    @ToString
    public static class BrimeChatEmote {
        @SerializedName("_id")
        private String emoteId;

        public String get1xImageUrl() {
            return String.format(IMAGE_URL_FORMAT, this.emoteId, "1x");
        }

        public String get2xImageUrl() {
            return String.format(IMAGE_URL_FORMAT, this.emoteId, "2x");
        }

        public String get3xImageUrl() {
            return String.format(IMAGE_URL_FORMAT, this.emoteId, "3x");
        }

    }

}
