package co.casterlabs.brimeapijava.requests;

import java.io.IOException;

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
public class BrimeDeleteChatMessageRequest extends AuthenticatedWebRequest<Void, BrimeUserAuth> {
    private String channelId;
    private String messageId;

    public BrimeDeleteChatMessageRequest(@NonNull BrimeUserAuth auth) {
        super(auth);
    }

    @Override
    protected Void execute() throws ApiException, ApiAuthException, IOException {
        if (this.channelId == null) {
            throw new ApiException("No channel id specified");
        } else if (this.messageId == null) {
            throw new ApiException("No message id specified");
        } else {
            String url = String.format("%s/internal/channel/%s/chat/message/%s", BrimeApi.targetApiEndpoint, this.channelId, this.messageId);

            Response response = HttpUtil.sendHttp("{}", "DELETE", url, this.auth);
            String body = response.body().string();

            response.close();

            if (response.code() != 200) {
                throw new ApiAuthException(body);
            }

            return null;
        }
    }

}
