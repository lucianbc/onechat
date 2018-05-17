package com.lucianbc.onechat.server;

import com.lucianbc.onechat.data.Message;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

class ChatRoom {

    private List<UserSessionId> userSessions = new LinkedList<>();
    @Getter private String roomId = UUID.randomUUID().toString();
    private final SessionManager sessionManager;
    private final WritePermissionManager writePermissionManager;

    ChatRoom(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.writePermissionManager = new WritePermissionManager(sessionManager, this);
    }

    void addUserSession(UserSessionId id) {
        Session session = sessionManager.getSession(id);
        if (session == null) return;
        userSessions.add(id);
        session.notifyAboutRoom(this);
    }

    void distributeMessage(Message<String, UserSessionId> message) {
        Session senderSession = sessionManager.getSession(message.getSender());
        if (senderSession == null) return;
        if (!writePermissionManager.canSend(message.getSender())) return;
        userSessions.forEach(usi -> {
            if (usi.equals(message.getSender())) return;
            Session session = sessionManager.getSession(usi);
            if (session == null) return;
            Message<String, Session> outMsg = new Message<>();
            outMsg.setRoom(message.getRoom());
            outMsg.setSender(senderSession);
            outMsg.setMessage(message.getMessage());
            session.sendMessage(outMsg);
        });
    }

    void handleWriteRequest(UserSessionId userId) {
        writePermissionManager.handleRequest(userId);
    }
}
