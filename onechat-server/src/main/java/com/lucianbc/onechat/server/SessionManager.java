package com.lucianbc.onechat.server;

import java.util.HashMap;
import java.util.Map;

class SessionManager {
    private Map<UserSessionId, Session> sessions = new HashMap<>();

    synchronized void registerSession(Session session) {
        sessions.forEach((key, value) -> {
            value.notifyAboutConnected(session);
            session.notifyAboutConnected(value);
        });
        sessions.put(session.getId(), session);
    }

    synchronized void dropSession(Session session) {
        sessions.remove(session.getId());
        sessions.forEach((k, v) -> v.notifyAboutDisconnected(session));
    }

    synchronized Session getSession(UserSessionId id) {
        return sessions.get(id);
    }
}
