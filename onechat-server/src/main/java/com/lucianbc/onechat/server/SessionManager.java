package com.lucianbc.onechat.server;

import java.util.LinkedList;
import java.util.List;

public class SessionManager {
    private List<Session> sessions = new LinkedList<>();

    public synchronized void registerSession(Session session) {
        sessions.forEach(s -> {
            s.notifyAboutConnected(session);
            session.notifyAboutConnected(s);
        });
        sessions.add(session);
    }

    public synchronized void dropSession(Session session) {
        sessions.remove(session);
        sessions.forEach(s -> s.notifyAboutDisconnected(session));
    }
}
