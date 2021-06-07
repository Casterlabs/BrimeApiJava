package co.casterlabs.brimeapijava.requests.categories;

import java.io.IOException;

import com.google.gson.JsonObject;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.BrimeApplicationAuth;
import co.casterlabs.brimeapijava.HttpUtil;
import co.casterlabs.brimeapijava.types.BrimeCategoryWithStreams;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Accessors(chain = true)
public class BrimeGetLiveByCategoryRequest extends AuthenticatedWebRequest<BrimeCategoryWithStreams, BrimeApplicationAuth> {
    private @Setter @NonNull String category;

    public BrimeGetLiveByCategoryRequest(@NonNull BrimeApplicationAuth auth) {
        super(auth);
    }

    @Override
    protected BrimeCategoryWithStreams execute() throws ApiException, ApiAuthException, IOException {
        if (this.category == null) {
            throw new ApiException("No category specified");
        } else {
            String url = String.format("%s/v1/category/%s/live", BrimeApi.targetApiEndpoint, this.category);

            try (Response response = HttpUtil.sendHttpGet(url, this.auth)) {
                String body = response.body().string();

                JsonObject json = BrimeApi.GSON.fromJson(body, JsonObject.class);

                if (json.has("errors")) {
                    throw new ApiAuthException(body);
                } else {
                    return BrimeApi.GSON.fromJson(json.getAsJsonObject("data"), BrimeCategoryWithStreams.class);
                }
            }
        }
    }

}
