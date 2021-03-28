package co.casterlabs.brimeapijava;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthProvider;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import okhttp3.Request.Builder;

@AllArgsConstructor
public class BrimeApplicationAuth implements AuthProvider {
    protected String clientId;

    @Override
    public void authenticateRequest(@NonNull Builder request) {
        request.addHeader("Client_Id", this.clientId);
    }

    @Override
    public void refresh() throws ApiAuthException {
        throw new UnsupportedOperationException();
    }

}
