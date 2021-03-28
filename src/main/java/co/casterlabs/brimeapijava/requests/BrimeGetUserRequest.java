package co.casterlabs.brimeapijava.requests;

import java.io.IOException;

import com.google.gson.JsonObject;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.BrimeApplicationAuth;
import co.casterlabs.brimeapijava.HttpUtil;
import co.casterlabs.brimeapijava.types.BrimeUser;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Accessors(chain = true)
public class BrimeGetUserRequest extends AuthenticatedWebRequest<BrimeUser, BrimeApplicationAuth> {
    private @Setter @NonNull String name;

    public BrimeGetUserRequest(@NonNull BrimeApplicationAuth auth) {
        super(auth);
    }

    @Override
    protected BrimeUser execute() throws ApiException, ApiAuthException, IOException {
        if (this.name == null) {
            throw new ApiException("No user specified");
        } else {
            Response response = HttpUtil.sendHttpGet(BrimeApi.targetApiEndpoint + "/v1/user/" + this.name, this.auth);
            String body = response.body().string();

            response.close();

            JsonObject json = BrimeApi.GSON.fromJson(body, JsonObject.class);

            if (json.has("errors")) {
                throw new ApiAuthException(body);
            } else {
                try {
                    return BrimeApi.GSON.fromJson(json.getAsJsonObject("data"), BrimeUser.class);
                } catch (Exception e) {
                    throw new ApiException(e);
                }
            }
        }
    }

}
