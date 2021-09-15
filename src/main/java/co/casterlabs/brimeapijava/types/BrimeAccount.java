package co.casterlabs.brimeapijava.types;

import java.time.Instant;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class BrimeAccount {
    private String xid;

    @JsonField("legacy_id")
    private String legacyId;

    private String email;

    @JsonField("email_verified")
    private boolean emailVerified;

    private String username;

    private String displayname;

    private Instant created;

    @JsonField("has_avatar")
    private boolean hasAvatar;

}
