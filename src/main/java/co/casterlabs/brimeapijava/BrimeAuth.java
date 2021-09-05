package co.casterlabs.brimeapijava;

import java.io.IOException;
import java.net.URLEncoder;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.auth.AuthProvider;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class BrimeAuth extends AuthProvider {
    private @Getter String refreshToken;
    private String clientId;
    private String secret;

    private @Getter String accessToken;
    private @Getter int expiresIn;
    private long loginTimestamp;

    public BrimeAuth(String refreshToken, String clientId, String secret) throws ApiAuthException {
        this.refreshToken = refreshToken;
        this.clientId = clientId;
        this.secret = secret;

        this.refresh();
    }

    @Override
    protected void authenticateRequest0(@NonNull Builder request) {
        request.addHeader("Authorization", "Bearer " + this.accessToken);
        request.addHeader("client_id", this.clientId);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void refresh() throws ApiAuthException {
        String form = String.format(
            "grant_type=refresh_token"
                + "&refresh_token=%s"
                + "&client_id=%s"
                + "&client_secret=%s",
            URLEncoder.encode(this.refreshToken),
            URLEncoder.encode(this.clientId),
            URLEncoder.encode(this.secret)
        );

        try (Response response = HttpUtil.sendHttp(form, "https://auth.brime.tv/oauth/token", null, "application/x-www-form-urlencoded")) {
            String body = response.body().string();

            JsonObject json = BrimeApi.RSON.fromJson(body, JsonObject.class);

            if (json.containsKey("error")) {
                throw new ApiAuthException(body);
            } else {
                AuthResponse data = BrimeApi.RSON.fromJson(json, AuthResponse.class);

                this.accessToken = data.accessToken;
                this.expiresIn = data.expiresIn;
            }
        } catch (IOException | JsonParseException e) {
            throw new ApiAuthException(e);
        }
    }

    @SuppressWarnings("deprecation")
    public static AuthResponse authorize(String code, String redirectUri, String clientId, String secret) throws IOException, ApiAuthException {
        String form = String.format(
            "grant_type=authorization_code"
                + "&code=%s"
                + "&redirect_uri=%s"
                + "&client_id=%s"
                + "&client_secret=%s",
            URLEncoder.encode(code),
            URLEncoder.encode(redirectUri),
            URLEncoder.encode(clientId),
            URLEncoder.encode(secret)
        );

        try (Response response = HttpUtil.sendHttp(form, "https://auth.brime.tv/oauth/token", null, "application/x-www-form-urlencoded")) {
            String body = response.body().string();

            JsonObject json = BrimeApi.RSON.fromJson(body, JsonObject.class);

            if (json.containsKey("error")) {
                throw new ApiAuthException(body);
            } else {
                return BrimeApi.RSON.fromJson(json, AuthResponse.class);
            }
        } catch (JsonParseException e) {
            throw new IOException(e); // :^)
        }
    }

    @Getter
    @ToString
    @JsonClass(exposeAll = true)
    public static class AuthResponse {
        @JsonField("access_token")
        private String accessToken;

        @JsonField("refresh_token")
        private String refreshToken;

        @JsonField("expires_in")
        private int expiresIn;

        @JsonField("token_type")
        private String tokenType;

    }

    @Override
    public boolean isApplicationAuth() {
        return false;
    }

    @Override
    public boolean isExpired() {
        return ((System.currentTimeMillis() - this.loginTimestamp) / 1000) > this.expiresIn;
    }

}
