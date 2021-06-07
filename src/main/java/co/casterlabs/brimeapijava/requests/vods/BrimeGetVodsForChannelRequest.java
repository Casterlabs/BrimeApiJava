package co.casterlabs.brimeapijava.requests.vods;

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
import co.casterlabs.brimeapijava.types.BrimePaginatedApiSort;
import co.casterlabs.brimeapijava.types.BrimeVod;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Accessors(chain = true)
public class BrimeGetVodsForChannelRequest extends AuthenticatedWebRequest<List<BrimeVod>, BrimeApplicationAuth> {
    private static final Type LIST_TYPE = new TypeToken<List<BrimeVod>>() {
    }.getType();

    private @Setter @NonNull String channel;
    private @Setter int limit = 50;
    private @Setter int skip = 0;
    private @Setter @NonNull BrimePaginatedApiSort sorting = BrimePaginatedApiSort.DESCENDING;

    public BrimeGetVodsForChannelRequest(@NonNull BrimeApplicationAuth auth) {
        super(auth);
    }

    @Override
    protected List<BrimeVod> execute() throws ApiException, ApiAuthException, IOException {
        if (this.channel == null) {
            throw new ApiException("No channel specified");
        } else {
            String url = String.format(
                "%s/v1/channel/%s/vods?limit=%d&skip=%d&sort=%s",
                BrimeApi.targetApiEndpoint,
                this.channel,
                this.limit,
                this.skip,
                this.sorting.toString()
            );

            try (Response response = HttpUtil.sendHttpGet(url, this.auth)) {
                String body = response.body().string();

                JsonObject json = BrimeApi.GSON.fromJson(body, JsonObject.class);

                if (json.has("errors")) {
                    throw new ApiAuthException(body);
                } else {
                    return BrimeApi.GSON.fromJson(json.getAsJsonObject("data").get("vods"), LIST_TYPE);
                }
            }
        }
    }

}
