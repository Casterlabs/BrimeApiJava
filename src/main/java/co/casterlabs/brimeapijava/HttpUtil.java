package co.casterlabs.brimeapijava;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.jetbrains.annotations.Nullable;

import lombok.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtil {
    private static OkHttpClient client = new OkHttpClient();

    public static Response sendHttpGet(@NonNull String address) throws IOException {
        return sendHttp(null, null, address, null);
    }

    public static Response sendHttp(@NonNull String body, @NonNull String address) throws IOException {
        return sendHttp(RequestBody.create(body.getBytes(StandardCharsets.UTF_8)), "POST", address, null);
    }

    public static Response sendHttp(@Nullable RequestBody body, @Nullable String type, @NonNull String address, @Nullable Map<String, String> headers) throws IOException {
        Request.Builder builder = new Request.Builder().url(address);

        if ((body != null) && (type != null)) {
            builder.method(type.toUpperCase(), body);
        }

        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }
        }

        builder.addHeader("x-client-type", "casterlabs-api-lib");

        Request request = builder.build();
        Response response = client.newCall(request).execute();

        return response;
    }

}
