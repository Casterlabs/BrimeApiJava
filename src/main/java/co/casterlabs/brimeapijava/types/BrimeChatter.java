package co.casterlabs.brimeapijava.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@JsonClass(exposeAll = true)
public class BrimeChatter {

    @JsonField("user_xid")
    private String userXid;

    private String username;

    private String displayname;

    @JsonField("guest")
    private boolean isGuest;

}
