package com.yourpackage.example;

import co.casterlabs.apiutil.web.ApiException;
import co.casterlabs.brimeapijava.requests.BrimeGetChannelRequest;
import co.casterlabs.brimeapijava.types.BrimeChannel;

public class GetChannelExample {

    public static void main(String[] args) {
        try {
            BrimeChannel channel = new BrimeGetChannelRequest("geeken").send();

            System.out.println(channel.getChannelName()); // geeken
            System.out.println(channel.getTitle()); // ...
            System.out.println(channel.getCategory()); // ...
        } catch (/* ApiAuthException | */ ApiException e) {
            e.printStackTrace();
        }
    }

}
