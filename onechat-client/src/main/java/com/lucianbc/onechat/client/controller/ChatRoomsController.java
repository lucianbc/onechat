package com.lucianbc.onechat.client.controller;

import com.lucianbc.onechat.client.model.ChatRoom;
import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.data.UserIdentity;

import java.util.HashMap;
import java.util.Map;

public class ChatRoomsController {
    private Map<String, ChatRoom> rooms = new HashMap<>();

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
}
