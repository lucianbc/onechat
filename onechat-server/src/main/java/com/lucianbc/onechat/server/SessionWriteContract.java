package com.lucianbc.onechat.server;

public interface SessionWriteContract {
    boolean isAvailable(UserSessionId id);
    void sendWriteAccess(UserSessionId id, boolean access, String roomId);
}
