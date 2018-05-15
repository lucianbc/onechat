package com.lucianbc.onechat.server;

import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private Map<UserSessionId, Session> sessions = new HashMap<>();

    public synchronized void registerSession(Session session) {
        sessions.forEach((key, value) -> {
            value.notifyAboutConnected(session);
            session.notifyAboutConnected(value);
        });
        sessions.put(session.getId(), session);
    }

    public synchronized void dropSession(Session session) {
        sessions.remove(session.getId());
        sessions.forEach((k, v) -> v.notifyAboutDisconnected(session));
    }

    public synchronized Session getSession(UserSessionId id) {
        return sessions.get(id);
    }
}
