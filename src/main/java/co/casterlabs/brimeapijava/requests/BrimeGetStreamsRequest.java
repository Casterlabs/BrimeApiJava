package co.casterlabs.brimeapijava.requests;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.HttpUtil;
import co.casterlabs.brimeapijava.types.BrimeStream;
import okhttp3.Response;

public class BrimeGetStreamsRequest extends WebRequest<List<BrimeStream>> {
    private static final Type LIST_TYPE = new TypeToken<List<BrimeStream>>() {
    }.getType();

    @Override
    protected List<BrimeStream> execute() throws ApiException, ApiAuthException, IOException {
        Response response = HttpUtil.sendHttpGet(BrimeApi.targetApiEndpoint + "/streams");
        String body = response.body().string();

        response.close();

        if (response.code() == 401) {
            throw new ApiAuthException(body);
        } else {
            try {
                JsonObject data = BrimeApi.GSON.fromJson(body, JsonObject.class);

                return BrimeApi.GSON.fromJson(data.get("streams"), LIST_TYPE);
            } catch (Exception e) {
                throw new ApiException(e);
            }
        }
    }

}
