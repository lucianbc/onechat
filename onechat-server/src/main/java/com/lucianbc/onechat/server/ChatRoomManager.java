package com.lucianbc.onechat.server;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ChatRoomManager {

    private final SessionManager sessionManager;

    private Map<String, ChatRoom> rooms = new HashMap<>();

    public void openRoom(List<UserSessionId> initialUsers) {
        ChatRoom room = new ChatRoom(sessionManager);
        initialUsers.forEach(room::addUserSession);
        rooms.put(room.getRoomId(), room);
    }

    private void handleIncommingMessage() {

    }
}
