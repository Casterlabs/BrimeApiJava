package co.casterlabs.brimeapijava.requests.emotes;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.BrimeApplicationAuth;
import co.casterlabs.brimeapijava.HttpUtil;
import co.casterlabs.brimeapijava.types.BrimeEmoteSet;
import lombok.NonNull;
import okhttp3.Response;

public class BrimeGetGlobalEmotesRequest extends AuthenticatedWebRequest<List<BrimeEmoteSet>, BrimeApplicationAuth> {
    private static final Type LIST_TYPE = new TypeToken<List<BrimeEmoteSet>>() {
    }.getType();

    public BrimeGetGlobalEmotesRequest(@NonNull BrimeApplicationAuth auth) {
        super(auth);
    }

    @Override
    protected List<BrimeEmoteSet> execute() throws ApiException, ApiAuthException, IOException {
        try (Response response = HttpUtil.sendHttpGet(BrimeApi.targetApiEndpoint + "/v1/emotesets", this.auth)) {
            String body = response.body().string();

            JsonObject json = BrimeApi.GSON.fromJson(body, JsonObject.class);

            if (json.has("errors")) {
                throw new ApiException(body);
            } else {
                return BrimeApi.GSON.fromJson(json.getAsJsonObject("data").get("emoteSets"), LIST_TYPE);
            }
        }
    }

}
