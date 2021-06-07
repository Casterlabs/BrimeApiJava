package co.casterlabs.brimeapijava.requests.streams;

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

public class BrimeGetAllLiveStreamsRequest extends AuthenticatedWebRequest<List<BrimeStream>, BrimeApplicationAuth> {
    private static final Type LIST_TYPE = new TypeToken<List<BrimeStream>>() {
    }.getType();

    public BrimeGetAllLiveStreamsRequest(@NonNull BrimeApplicationAuth auth) {
        super(auth);
    }

    @Override
    protected List<BrimeStream> execute() throws ApiException, ApiAuthException, IOException {
        String url = String.format("%s/v1/streams", BrimeApi.targetApiEndpoint);

        try (Response response = HttpUtil.sendHttpGet(url, this.auth)) {
            String body = response.body().string();

            JsonObject json = BrimeApi.GSON.fromJson(body, JsonObject.class);

            if (json.has("errors")) {
                throw new ApiAuthException(body);
            } else {
                return BrimeApi.GSON.fromJson(json.getAsJsonObject("data").get("streams"), LIST_TYPE);
            }
        }
    }

}
