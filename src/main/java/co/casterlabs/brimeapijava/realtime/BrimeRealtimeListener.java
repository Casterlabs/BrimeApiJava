package co.casterlabs.brimeapijava.realtime;

public interface BrimeRealtimeListener {

    default void onOpen() {}

    public void onChat(BrimeChatMessage chat);

    public void onFollow(String username, String userId);

    public void onJoin(String username);

    public void onLeave(String username);

    default void onClose() {}

    public void onSub(String username, String userId, boolean isResub);

    public void onRaid(String channel, String channelId, int viewers);

    public void onChatDelete(String messageId);

}
