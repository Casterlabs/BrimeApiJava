package co.casterlabs.brimeapijava.requests.categories;

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
import co.casterlabs.brimeapijava.types.BrimeCategoryWithStreams;
import lombok.NonNull;
import okhttp3.Response;

public class BrimeGetLiveCategoriesRequest extends AuthenticatedWebRequest<List<BrimeCategoryWithStreams>, BrimeApplicationAuth> {
    private static final Type LIST_TYPE = new TypeToken<List<BrimeCategoryWithStreams>>() {
    }.getType();

    public BrimeGetLiveCategoriesRequest(@NonNull BrimeApplicationAuth auth) {
        super(auth);
    }

    @Override
    protected List<BrimeCategoryWithStreams> execute() throws ApiException, ApiAuthException, IOException {
        String url = String.format("%s/v1/categories/live", BrimeApi.targetApiEndpoint);

        try (Response response = HttpUtil.sendHttpGet(url, this.auth)) {
            String body = response.body().string();

            JsonObject json = BrimeApi.GSON.fromJson(body, JsonObject.class);

            if (json.has("errors")) {
                throw new ApiException(body);
            } else {
                return BrimeApi.GSON.fromJson(json.getAsJsonObject("data").get("categories"), LIST_TYPE);
            }
        }
    }

}
