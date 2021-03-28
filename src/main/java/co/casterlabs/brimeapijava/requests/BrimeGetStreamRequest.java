package co.casterlabs.brimeapijava.requests;

import java.io.IOException;

import com.google.gson.JsonObject;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.BrimeApplicationAuth;
import co.casterlabs.brimeapijava.HttpUtil;
import co.casterlabs.brimeapijava.types.BrimeStream;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Accessors(chain = true)
public class BrimeGetStreamRequest extends AuthenticatedWebRequest<BrimeStream, BrimeApplicationAuth> {
    private @Setter @NonNull String channel;

    public BrimeGetStreamRequest(@NonNull BrimeApplicationAuth auth) {
        super(auth);
    }

    @Override
    protected BrimeStream execute() throws ApiException, ApiAuthException, IOException {
        if (this.channel == null) {
            throw new ApiException("No channel specified");
        } else {
            Response response = HttpUtil.sendHttpGet(BrimeApi.targetApiEndpoint + "/v1/stream/" + this.channel, this.auth);
            String body = response.body().string();

            response.close();

            JsonObject json = BrimeApi.GSON.fromJson(body, JsonObject.class);

            if (json.has("errors")) {
                throw new ApiAuthException(body);
            } else {
                try {
                    return BrimeApi.GSON.fromJson(json.getAsJsonObject("data"), BrimeStream.class);
                } catch (Exception e) {
                    throw new ApiException(e);
                }
            }
        }
    }

}