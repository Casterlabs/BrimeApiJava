package co.casterlabs.brimeapijava.realtime;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class BrimeChatMessage {
    @JsonField("xid")
    private String messageId;

    private String channel;

    private Instant timestamp;

    private BrimeChatMessage reply;

    @JsonField("user")
    private BrimeMessageSender sender;

    private BrimeMessageContent content;

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class BrimeMessageSender {
        private String xid;

        @JsonField("legacy_id")
        private String legacyId;

        private String username;

        private String displayname;

        private String color;

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class BrimeMessageContent {
        private String type;
        private String raw;
        private String parsed;

        private BrimeMessageMeta meta;

        @Getter
        @ToString
        @JsonClass(exposeAll = true)
        public static class BrimeMessageMeta {
            private BrimeMessageSender[] mentions;

            private BrimeMessageLink[] links;

            private BrimeMessageEmote[] emotes;

            @JsonField("attachements")
            private BrimeMessageAttachment[] attachments;

            @Getter
            @ToString
            @JsonClass(exposeAll = true)
            public static class BrimeMessageEmote {
                private String xid;
                private String code;
                private String src;
            }

            @Getter
            @ToString
            @JsonClass(exposeAll = true)
            public static class BrimeMessageLink {
                private String match;
                private String host;
            }

            @Getter
            @ToString
            @JsonClass(exposeAll = true)
            public static class BrimeMessageAttachment {
                private String type;
                private String mime;
                private String src;
                private String preview;
            }
        }
    }

}
