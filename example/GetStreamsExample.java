package co.casterlabs.brimeapijava;

import java.util.List;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.brimeapijava.requests.BrimeGetStreamsRequest;
import co.casterlabs.brimeapijava.types.BrimeStream;

public class GetStreamsExample {

    public static void main(String[] args) {
        try {
            List<BrimeStream> streams = new BrimeGetStreamsRequest().send();

            for (BrimeStream stream : streams) {
                System.out.println(stream.getStreamer()); // ...
                /* For more info, see:
                 * https://github.com/Casterlabs/BrimeApiJava/blob/main/src/main/java/co/casterlabs/brimeapijava/types/BrimeStream.java
                 */
            }
        } catch (/* ApiAuthException | */ ApiException e) {
            e.printStackTrace();
        }
    }

}
