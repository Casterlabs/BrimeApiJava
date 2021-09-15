package co.casterlabs.brimeapijava.requests;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.HttpUtil;
import co.casterlabs.brimeapijava.types.BrimeChatter;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import okhttp3.Response;

public class BrimeGetChattersRequest extends WebRequest<List<BrimeChatter>> {
    private String query;

    @Deprecated
    public BrimeGetChattersRequest queryBySlug(String slug) {
        this.query = slug;
        return this;
    }

    @Override
    protected List<BrimeChatter> execute() throws ApiException, ApiAuthException, IOException {
        String url = String.format("https://api.brime.tv/v1/chat/channel/%s/chatters", this.query);

        try (Response response = HttpUtil.sendHttpGet(url, null)) {
            String body = response.body().string();

            if (response.code() == 200) {
                JsonObject json = BrimeApi.RSON.fromJson(body, JsonObject.class);

                List<BrimeChatter> result = new LinkedList<>();

                for (JsonElement chatter : json.getArray("chatters")) {
                    result.add(BrimeApi.RSON.fromJson(chatter, BrimeChatter.class));
                }

                return result;
            } else {
                throw new ApiException(body);
            }
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
