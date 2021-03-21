package co.casterlabs.brimeapijava.requests;

import java.io.IOException;

import com.google.gson.JsonObject;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.HttpUtil;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class BrimeSendChatMessageRequest extends WebRequest<Void> {
    private @NonNull String token;

    private String channel;
    private String color;
    private String username;
    private String message;

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        JsonObject payload = new JsonObject();

        payload.addProperty("color", this.color);
        payload.addProperty("token", this.token);
        payload.addProperty("channel", this.channel);
        payload.addProperty("message", this.message);
        payload.addProperty("username", this.username);

        Response response = HttpUtil.sendHttp(payload.toString(), BrimeApi.targetApiEndpoint + "/chat/send");
        String body = response.body().string();

        response.close();

        if ((response.code() == 401) || (response.code() == 403)) {
            throw new ApiAuthException(body);
        } else if ((response.code() == 404) || body.isEmpty()) {
            throw new ApiException("Invalid channel name");
        }

        return null;
    }

}
