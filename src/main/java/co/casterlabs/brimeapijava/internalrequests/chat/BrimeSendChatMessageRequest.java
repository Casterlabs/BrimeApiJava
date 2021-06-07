package co.casterlabs.brimeapijava.internalrequests.chat;

import java.io.IOException;

import com.google.gson.JsonObject;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.BrimeUserAuth;
import co.casterlabs.brimeapijava.HttpUtil;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Setter
@NonNull
@Accessors(chain = true)
public class BrimeSendChatMessageRequest extends AuthenticatedWebRequest<Void, BrimeUserAuth> {
    private String color = "#ea4c4c";
    private String channelId;
    private String message;

    public BrimeSendChatMessageRequest(@NonNull BrimeUserAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        if (this.channelId == null) {
            throw new ApiException("No channel id specified");
        } else if (this.message == null) {
            throw new ApiException("No message specified");
        } else {
            JsonObject payload = new JsonObject();

            payload.addProperty("color", this.color);
            payload.addProperty("message", this.message);

            String url = String.format("%s/internal/chat/send/%s", BrimeApi.targetApiEndpoint, this.channelId);

            try (Response response = HttpUtil.sendHttp(payload.toString(), url, this.auth)) {
                if (response.code() != 200) {
                    String body = response.body().string();

                    throw new ApiAuthException(body);
                }

                return null;
            }
        }
    }

}
