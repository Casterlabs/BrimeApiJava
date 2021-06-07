package co.casterlabs.brimeapijava.requests.emotes;

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
import co.casterlabs.brimeapijava.types.BrimeEmote;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import okhttp3.Response;

@Accessors(chain = true)
public class BrimeGetChannelEmotesRequest extends AuthenticatedWebRequest<List<BrimeEmote>, BrimeApplicationAuth> {
    private static final Type LIST_TYPE = new TypeToken<List<BrimeEmote>>() {
    }.getType();

    private @Setter @NonNull String channel;

    public BrimeGetChannelEmotesRequest(@NonNull BrimeApplicationAuth auth) {
        super(auth);
    }

    @Override
    protected List<BrimeEmote> execute() throws ApiException, ApiAuthException, IOException {
        if (this.channel == null) {
            throw new ApiException("No channel specified");
        } else {
            String url = String.format("%s/v1/channel/%s/emotes", BrimeApi.targetApiEndpoint, this.channel);

            try (Response response = HttpUtil.sendHttpGet(url, this.auth)) {
                String body = response.body().string();

                JsonObject json = BrimeApi.GSON.fromJson(body, JsonObject.class);

                if (json.has("errors")) {
                    throw new ApiException(body);
                } else {
                    return BrimeApi.GSON.fromJson(json.getAsJsonObject("data").get("emotes"), LIST_TYPE);
                }
            }
        }
    }

}
