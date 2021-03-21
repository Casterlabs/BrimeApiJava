package co.casterlabs.brimeapijava.realtime;

public interface BrimeRealtimeListener {

    default void onOpen() {}

    public void onChat(String username, String color, String message);

    public void onFollow(String username, String id);

    public void onJoin(String username);

    public void onLeave(String username);

    default void onClose() {}

}
