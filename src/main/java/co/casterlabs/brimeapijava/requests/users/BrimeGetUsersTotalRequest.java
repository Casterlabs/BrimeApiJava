package co.casterlabs.brimeapijava.requests.users;

import java.io.IOException;

import com.google.gson.JsonObject;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.BrimeApplicationAuth;
import co.casterlabs.brimeapijava.HttpUtil;
import lombok.NonNull;
import okhttp3.Response;

public class BrimeGetUsersTotalRequest extends AuthenticatedWebRequest<Integer, BrimeApplicationAuth> {

    public BrimeGetUsersTotalRequest(@NonNull BrimeApplicationAuth auth) {
        super(auth);
    }

    @Override
    protected Integer execute() throws ApiException, ApiAuthException, IOException {
        String url = String.format("%s/v1/users", BrimeApi.targetApiEndpoint);

        try (Response response = HttpUtil.sendHttpGet(url, this.auth)) {
            String body = response.body().string();

            JsonObject json = BrimeApi.GSON.fromJson(body, JsonObject.class);

            if (json.has("errors")) {
                throw new ApiException(body);
            } else {
                return json.getAsJsonObject("data").get("total").getAsInt();
            }
        }
    }

}
