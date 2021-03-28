package co.casterlabs.brimeapijava;

import co.casterlabs.apiutil.auth.ApiAuthException;
import lombok.NonNull;
import okhttp3.Request.Builder;

public class BrimeUserAuth extends BrimeApplicationAuth {
    protected String accessToken;

    public BrimeUserAuth(@NonNull String clientId, @NonNull String accessToken) {
        super(clientId);

        this.accessToken = accessToken;
    }

    @Override
    public void authenticateRequest(@NonNull Builder request) {
        request.addHeader("Client_Id", this.clientId);
        request.addHeader("Authorization", "Bearer " + this.accessToken);
    }

    @Override
    public void refresh() throws ApiAuthException {
        throw new UnsupportedOperationException();
    }

}
