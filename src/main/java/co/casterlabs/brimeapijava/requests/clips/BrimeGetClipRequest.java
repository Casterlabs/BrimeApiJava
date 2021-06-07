package co.casterlabs.brimeapijava.requests.clips;

import java.io.IOException;

import com.google.gson.JsonObject;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.BrimeApplicationAuth;
import co.casterlabs.brimeapijava.HttpUtil;
import co.casterlabs.brimeapijava.types.BrimeClip;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Accessors(chain = true)
public class BrimeGetClipRequest extends AuthenticatedWebRequest<BrimeClip, BrimeApplicationAuth> {
    private @Setter @NonNull String clip;

    public BrimeGetClipRequest(@NonNull BrimeApplicationAuth auth) {
        super(auth);
    }

    @Override
    protected BrimeClip execute() throws ApiException, ApiAuthException, IOException {
        if (this.clip == null) {
            throw new ApiException("No clip specified");
        } else {
            String url = String.format("%s/v1/clip/%s", BrimeApi.targetApiEndpoint, this.clip);

            try (Response response = HttpUtil.sendHttpGet(url, this.auth)) {
                String body = response.body().string();

                JsonObject json = BrimeApi.GSON.fromJson(body, JsonObject.class);

                if (json.has("errors")) {
                    throw new ApiAuthException(body);
                } else {
                    return BrimeApi.GSON.fromJson(json.getAsJsonObject("data"), BrimeClip.class);
                }
            }
        }
    }

}
