package co.casterlabs.brimeapijava.requests.emotes;

import java.io.IOException;

import com.google.gson.JsonObject;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.BrimeApplicationAuth;
import co.casterlabs.brimeapijava.HttpUtil;
import co.casterlabs.brimeapijava.types.BrimeEmoteSet;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Accessors(chain = true)
public class BrimeGetEmoteSetRequest extends AuthenticatedWebRequest<BrimeEmoteSet, BrimeApplicationAuth> {
    private @Setter @NonNull String emoteSetId;

    public BrimeGetEmoteSetRequest(@NonNull BrimeApplicationAuth auth) {
        super(auth);
    }

    @Override
    protected BrimeEmoteSet execute() throws ApiException, ApiAuthException, IOException {
        if (this.emoteSetId == null) {
            throw new ApiException("No emote set specified");
        } else {
            try (Response response = HttpUtil.sendHttpGet(BrimeApi.targetApiEndpoint + "/v1/emoteset/" + this.emoteSetId, this.auth)) {
                String body = response.body().string();

                JsonObject json = BrimeApi.GSON.fromJson(body, JsonObject.class);

                if (json.has("errors")) {
                    throw new ApiException(body);
                } else {
                    return BrimeApi.GSON.fromJson(json.getAsJsonObject("data"), BrimeEmoteSet.class);
                }
            }
        }
    }

}
