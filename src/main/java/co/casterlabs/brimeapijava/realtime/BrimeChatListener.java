package co.casterlabs.brimeapijava.realtime;

public interface BrimeChatListener {

    default void onOpen() {}

    public void onChat(BrimeChatMessage chat);

    default void onClose(boolean remote) {}

}
