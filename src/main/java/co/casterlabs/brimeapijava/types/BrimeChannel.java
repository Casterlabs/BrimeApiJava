package co.casterlabs.brimeapijava.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class BrimeChannel {
    private BrimeChannelData channel;
    private BrimeChannelOwner owner;
    private BrimeChannelStream stream;

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class BrimeChannelData {
        private String xid;

        @JsonField("legacy_id")
        private String legacyId;

        private String slug;

        private String status;

        @JsonField("stream_category_slug")
        private String streamCategorySlug;

        @JsonField("is_live")
        private int isLive;

        private String displayname;

        public boolean isLive() {
            return this.isLive > 0;
        }

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class BrimeChannelOwner {
        @JsonField("channel_owner_xid")
        private String channelOwnerXid;

        @JsonField("channel_owner_legacy_id")
        private String channelOwnerLegacyId;

    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class BrimeChannelStream {
        private String title;

        @JsonField("category_name")
        private String category_name;

        @JsonField("category_xid")
        private String categoryXid;

        @JsonField("category_slug")
        private String categorySlug;

    }

}
