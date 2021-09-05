package co.casterlabs.brimeapijava.requests;

import java.io.IOException;

import co.casterlabs.apiutil.auth.ApiAuthException;
import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.apiutil.web.WebRequest;
import co.casterlabs.brimeapijava.BrimeApi;
import co.casterlabs.brimeapijava.HttpUtil;
import co.casterlabs.brimeapijava.types.BrimeChannel;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import okhttp3.Response;

public class BrimeGetChannelRequest extends WebRequest<BrimeChannel> {
    private String query;

    public BrimeGetChannelRequest queryByXid(String xid) {
        this.query = xid;
        return this;
    }

    public BrimeGetChannelRequest queryBySlug(String slug) {
        this.query = "slug/" + slug;
        return this;
    }

    @Override
    protected BrimeChannel execute() throws ApiException, ApiAuthException, IOException {
        String url = String.format("https://api.brime.tv/v1/channels/%s", this.query);

        try (Response response = HttpUtil.sendHttpGet(url, null)) {
            String body = response.body().string();

            if (response.code() == 200) {
                return BrimeApi.RSON.fromJson(body, BrimeChannel.class);
            } else {
                throw new ApiException(body);
            }
        } catch (JsonParseException e) {
            throw new ApiException(e);
        }
    }

}
