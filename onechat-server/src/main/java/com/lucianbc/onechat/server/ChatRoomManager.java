package com.lucianbc.onechat.server;

import com.lucianbc.onechat.data.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ChatRoomManager {

    private static final Logger logger = LoggerFactory.getLogger(ChatRoomManager.class);

    private final SessionManager sessionManager;

    private final ChatRoom commonRoom;


    private Map<String, ChatRoom> rooms = new HashMap<>();

    ChatRoomManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.commonRoom = new ChatRoom(sessionManager, "ddb5fe2f-5ae7-4cfc-afdc-0d09a0cc2d78");
    }

    synchronized void openRoom(List<UserSessionId> initialUsers) {
        ChatRoom room = new ChatRoom(sessionManager);
        initialUsers.forEach(room::addUserSession);
        rooms.put(room.getRoomId(), room);
    }

    synchronized void handleMessage(Message<String, UserSessionId> message) {
        ChatRoom targetRoom = solveRoom(message.getRoom());
        if (targetRoom == null) {
            logger.error("Room not initialized");
            return;
        }
        targetRoom.distributeMessage(message);
    }

    synchronized void handleWriteRequest(String roomId, UserSessionId userId) {
        ChatRoom room = solveRoom(roomId);
        if (room == null) {
            return;
        }
        room.handleWriteRequest(userId);
    }

    private ChatRoom solveRoom(String roomId) {
        if (commonRoom.getRoomId().equals(roomId)) return commonRoom;
        return rooms.get(roomId);
    }

    void registerToCommonRoom(UserSessionId id) {
        commonRoom.addUserSession(id);
    }

    synchronized ChatRoom getRoom(String roomId) {
        return solveRoom(roomId);
    }
}
