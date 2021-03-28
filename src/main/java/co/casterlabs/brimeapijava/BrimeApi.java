package co.casterlabs.brimeapijava;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BrimeApi {
    public static final String PRODUCTION_API = "https://api.brimelive.com";
    public static final String STAGING_API = "https://api-staging.brimelive.com";

    /**
     * Change this as needed.
     */
    public static String targetApiEndpoint = PRODUCTION_API;

    // @formatter:off
    public static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .create();
    // @formatter:on

}
