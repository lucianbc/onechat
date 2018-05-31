package com.lucianbc.onechat.server;

import com.lucianbc.onechat.data.Message;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
class ChatRoomManager {

    private static final Logger logger = LoggerFactory.getLogger(ChatRoomManager.class);

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
            logger.error("Room not initialized");
            return;
        }
        targetRoom.distributeMessage(message);
    }

    synchronized void handleWriteRequest(String roomId, UserSessionId userId) {
        ChatRoom room = rooms.get(roomId);
        if (room == null) {
            return;
        }
        room.handleWriteRequest(userId);
    }
}
