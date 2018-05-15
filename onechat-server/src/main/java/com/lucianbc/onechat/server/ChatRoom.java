package com.lucianbc.onechat.server;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class ChatRoom {
    private List<UserSessionId> userSessions = new LinkedList<>();
    @Getter private String roomId = UUID.randomUUID().toString();
    private final SessionManager sessionManager;

    public void addUserSession(UserSessionId id) {
        Session session = sessionManager.getSession(id);
        if (session == null) return;
        userSessions.add(id);
        session.notifyAboutRoom(this);
    }
}
