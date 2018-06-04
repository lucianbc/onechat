package com.lucianbc.onechat.server;

import java.util.HashMap;
import java.util.Map;

class SessionManager implements SessionWriteContract {
    private Map<UserSessionId, Session> sessions = new HashMap<>();

    synchronized void registerSession(Session session) {
        sessions.forEach((key, value) -> {
            value.notifyAboutConnected(session);
            session.notifyAboutConnected(value);
        });
        sessions.put(session.getId(), session);
    }

    synchronized void dropSession(Session session) {
        if (session == null) return;
        sessions.remove(session.getId());
        sessions.forEach((k, v) -> v.notifyAboutDisconnected(session));
    }

    synchronized Session getSession(UserSessionId id) {
        return sessions.get(id);
    }

    @Override
    public boolean isAvailable(UserSessionId id) {
        Session session = getSession(id);
        return session != null;
    }

    @Override
    public void sendWriteAccess(UserSessionId id, boolean access, String roomId) {
        Session session = getSession(id);
        if (session == null) return;
        session.setWriteAccess(roomId, access);
    }
}
