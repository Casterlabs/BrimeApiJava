package co.casterlabs.brimeapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.HttpUtil;
import okhttp3.Response;

public class BrimeGetAccountsTotalRequest extends WebRequest<Integer> {

    @Override
    protected Integer execute() throws ApiException, ApiAuthException, IOException {
        Response response = HttpUtil.sendHttpGet(BrimeApi.targetApiEndpoint + "/accounts/total/");
        String body = response.body().string();

        response.close();

        return Integer.parseInt(body);
    }

}
