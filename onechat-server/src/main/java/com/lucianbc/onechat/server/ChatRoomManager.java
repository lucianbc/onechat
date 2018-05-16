package com.lucianbc.onechat.server;

import com.lucianbc.onechat.data.Message;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
class ChatRoomManager {

    private final SessionManager sessionManager;

    private Map<String, ChatRoom> rooms = new HashMap<>();

    synchronized void openRoom(List<UserSessionId> initialUsers) {
        ChatRoom room = new ChatRoom(sessionManager);
        initialUsers.forEach(room::addUserSession);
        rooms.put(room.getRoomId(), room);
    }

    synchronized void handleMessage(Message<String, UserSessionId> message) {
        ChatRoom targetRoom = rooms.get(message.getRoom());
        if (targetRoom == null) {
            System.out.println("Room not initialized");
            return;
        }
        targetRoom.distributeMessage(message);
    }
}
