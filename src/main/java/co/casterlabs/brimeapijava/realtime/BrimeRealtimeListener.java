package co.casterlabs.brimeapijava.realtime;

public interface BrimeRealtimeListener {

    default void onOpen() {}

    public void onChat(String username, String color, String message);

    public void onFollow(String username, String id);

    default void onClose() {}

}
