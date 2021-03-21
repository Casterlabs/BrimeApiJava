package co.casterlabs.brimeapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.HttpUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import okhttp3.Response;

@AllArgsConstructor
public class BrimeGetChannelFollowerCountRequest extends WebRequest<Integer> {
    private @NonNull String channel;

    @Override
    protected Integer execute() throws ApiException, ApiAuthException, IOException {
        Response response = HttpUtil.sendHttpGet(BrimeApi.targetApiEndpoint + "/channel/" + this.channel + "/followers");
        String body = response.body().string();

        response.close();

        if (response.code() == 401) {
            throw new ApiAuthException(body);
        } else if ((response.code() == 404) || body.isEmpty()) {
            throw new ApiException("Invalid channel name");
        } else {
            return Integer.parseInt(body);
        }
    }

}
