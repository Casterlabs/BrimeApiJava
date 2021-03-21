package co.casterlabs.brimeapijava.realtime;

public interface BrimeChatListener {

    default void onOpen() {}

    public void onChat(String username, String color, String message);

    default void onClose() {}

}
