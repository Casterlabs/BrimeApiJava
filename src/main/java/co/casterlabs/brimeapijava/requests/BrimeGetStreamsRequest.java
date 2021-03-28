package co.casterlabs.brimeapijava.requests;

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
import co.casterlabs.brimeapijava.types.BrimeStream;
import lombok.NonNull;
import okhttp3.Response;

public class BrimeGetStreamsRequest extends AuthenticatedWebRequest<List<BrimeStream>, BrimeApplicationAuth> {
    private static final Type LIST_TYPE = new TypeToken<List<BrimeStream>>() {
    }.getType();

    public BrimeGetStreamsRequest(@NonNull BrimeApplicationAuth auth) {
        super(auth);
    }

    @Override
    protected List<BrimeStream> execute() throws ApiException, ApiAuthException, IOException {
        Response response = HttpUtil.sendHttpGet(BrimeApi.targetApiEndpoint + "/v1/streams", this.auth);
        String body = response.body().string();

        response.close();

        JsonObject json = BrimeApi.GSON.fromJson(body, JsonObject.class);

        if (json.has("errors")) {
            throw new ApiAuthException(body);
        } else {
            try {
                return BrimeApi.GSON.fromJson(json.getAsJsonObject("data").get("streams"), LIST_TYPE);
            } catch (Exception e) {
                throw new ApiException(e);
            }
        }
    }

}
