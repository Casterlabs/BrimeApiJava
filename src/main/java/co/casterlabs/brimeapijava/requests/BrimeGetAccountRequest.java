package co.casterlabs.brimeapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.AuthenticatedWebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.BrimeAuth;
import co.casterlabs.brimeapijava.HttpUtil;
import co.casterlabs.brimeapijava.types.BrimeAccount;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.NonNull;
import okhttp3.Response;

public class BrimeGetAccountRequest extends AuthenticatedWebRequest<BrimeAccount, BrimeAuth> {

    public BrimeGetAccountRequest(@NonNull BrimeAuth auth) {
        super(auth);
    }

    @Override
    protected BrimeAccount execute() throws ApiException, ApiAuthException, IOException {
        try (Response response = HttpUtil.sendHttpGet("https://api.brime.tv/v1/account/me", this.auth)) {
            String body = response.body().string();

            if (response.code() == 200) {
                return BrimeApi.RSON.fromJson(body, BrimeAccount.class);
            } else {
                throw new ApiException(body);
            }
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
