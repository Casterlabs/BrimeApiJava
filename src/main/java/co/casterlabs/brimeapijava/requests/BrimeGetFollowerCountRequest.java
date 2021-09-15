package co.casterlabs.brimeapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.HttpUtil;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import okhttp3.Response;

public class BrimeGetFollowerCountRequest extends WebRequest<Integer> {
    private String query;

    public BrimeGetFollowerCountRequest queryByXid(String xid) {
        this.query = xid;
        return this;
    }

    @Override
    protected Integer execute() throws ApiException, ApiAuthException, IOException {
        String url = String.format("https://api.brime.tv/v1/channels/%s/follower_count", this.query);

        try (Response response = HttpUtil.sendHttpGet(url, null)) {
            String body = response.body().string();

            if (response.code() == 200) {
                JsonObject json = BrimeApi.RSON.fromJson(body, JsonObject.class);

                return json
                    .getObject("data")
                    .getNumber("follower_count")
                    .intValue();
            } else {
                throw new ApiException(body);
            }
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
