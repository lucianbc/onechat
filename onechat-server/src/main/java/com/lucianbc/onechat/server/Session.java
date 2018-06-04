package com.lucianbc.onechat.server;

import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.data.RoomAccess;
import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
class Session {
    private final UserIdentity user;
    private final NetworkEndpoint networkEndpoint;

    void notifyAboutConnected(Session userSession) {
        networkEndpoint.send("/connectedUser", userSession.user);
    }

    void notifyAboutDisconnected(Session userSession) {
        networkEndpoint.send("/disconnectedUser", userSession.user);
    }

    private void notifyAboutRoom(ChatRoom chatRoom) {
        networkEndpoint.send("/rooms", chatRoom.getRoomId());
    }

    UserSessionId getId() {
        return new UserSessionId(user.getId());
    }

    void sendMessage(Message message) {
        networkEndpoint.send("/incomingMessages", message);
    }

    void setWriteAccess(String roomId, boolean access) {
        networkEndpoint.send("/writeAccess", new RoomAccess(roomId, access));
    }

    void exitRoom(ChatRoom room) {
        room.disconnectSession(this);
    }

    void enterRoom(ChatRoom room) {
        notifyAboutRoom(room);
    }
}
