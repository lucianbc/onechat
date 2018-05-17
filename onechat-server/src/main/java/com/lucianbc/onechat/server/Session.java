package com.lucianbc.onechat.server;

import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.data.RoomAccess;
import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import lombok.AllArgsConstructor;

@AllArgsConstructor
class Session {
    private UserIdentity user;
    private NetworkEndpoint networkEndpoint;

    void notifyAboutConnected(Session userSession) {
        networkEndpoint.send("/connectedUser", userSession.user);
    }

    void notifyAboutDisconnected(Session userSession) {
        networkEndpoint.send("/disconnectedUser", userSession.user);
    }

    void notifyAboutRoom(ChatRoom chatRoom) {
        networkEndpoint.send("/rooms", chatRoom.getRoomId());
    }

    UserSessionId getId() {
        return new UserSessionId(user.getId());
    }

    void sendMessage(Message<String, Session> message) {
        networkEndpoint.send("/incomingMessages", message);
    }

    void setWriteAccess(String roomId, boolean access) {
        networkEndpoint.send("/writeAccess", new RoomAccess(roomId, access));
    }
}
