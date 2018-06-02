package com.lucianbc.onechat.client.controller;

import com.lucianbc.onechat.client.action.ActionDispatcher;
import com.lucianbc.onechat.client.model.ChatRoom;
import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.data.UserIdentity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatRoomsController {
    private Map<String, ChatRoom> rooms = new HashMap<>();
    private final ActionDispatcher actionDispatcher;

    public ChatRoomsController(ActionDispatcher actionDispatcher) {
        this.actionDispatcher = actionDispatcher;
    }

    public ChatRoom registerRoom(String roomId) {
        ChatRoom r = new ChatRoom(roomId);
        rooms.put(roomId, r);
        return r;
    }

    public void handleMessage(Message<String, UserIdentity> m) {
        ChatRoom r = rooms.get(m.getRoom());
        if (r == null) return;
        r.receiveMessage(m);
    }

    public void grantWritePermission(String roomId) {
        ChatRoom room = rooms.get(roomId);
        if (room == null) return;

        room.grantWritePermission();
    }

    public void removeWritePermission(String roomId) {
        ChatRoom room = rooms.get(roomId);
        if (room == null) return;

        room.removeWritePermission();
    }

    public void closeRoom(ChatRoom room) {
        actionDispatcher.dispatch(actionDispatcher.factory().leaveRoom(room));
        rooms.remove(room.getRoomId());
    }

    public void closeRooms() {
        Iterator<Map.Entry<String, ChatRoom>> it = rooms.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ChatRoom> e = it.next();
            actionDispatcher.dispatch(actionDispatcher.factory().leaveRoom(e.getValue()));
            it.remove();
        }
    }
}
