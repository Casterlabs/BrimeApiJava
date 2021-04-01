package co.casterlabs.brimeapijava;

import java.io.IOException;

import com.google.gson.JsonObject;

import co.casterlabs.apiutil.auth.ApiAuthException;
import lombok.NonNull;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class BrimeUserAuth extends BrimeApplicationAuth {
    protected String refreshToken;
    protected String accessToken;

    public BrimeUserAuth(@NonNull String clientId, @NonNull String refreshToken) throws ApiAuthException {
        super(clientId);

        this.refreshToken = refreshToken;

        this.refresh();
    }

    @Override
    public void authenticateRequest(@NonNull Builder request) {
        request.addHeader("Client_Id", this.clientId);

        if (this.accessToken != null) {
            request.addHeader("Authorization", "Bearer " + this.accessToken);
        }
    }

    @Override
    public void refresh() throws ApiAuthException {
        JsonObject payload = new JsonObject();

        payload.addProperty("refreshToken", this.refreshToken);

        try {
            Response response = HttpUtil.sendHttp(payload.toString(), BrimeApi.targetApiEndpoint + "/internal/auth/refresh", this);
            String body = response.body().string();

            response.close();

            JsonObject json = BrimeApi.GSON.fromJson(body, JsonObject.class);

            if (json.has("errors")) {
                throw new ApiAuthException(body);
            } else {
                JsonObject data = json.getAsJsonObject("data");

                this.refreshToken = data.get("refreshToken").getAsString();
                this.accessToken = data.get("accessToken").getAsString();
            }
        } catch (IOException e) {
            throw new ApiAuthException(e);
        }
    }

}
