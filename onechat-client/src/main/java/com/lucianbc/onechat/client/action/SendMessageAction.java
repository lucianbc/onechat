package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.model.ChatRoom;
import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendMessageAction implements Action {
    private final UserIdentity sender;
    private final ChatRoom chatRoom;
    private final String message;
    private final NetworkEndpoint networkEndpoint;

    @Override
    public void fire() {
        Message<String, String> msg = new Message<>();
        msg.setSender(sender.getId());
        msg.setRoom(chatRoom.getRoomId());
        msg.setMessage(message);
        networkEndpoint.send("/messages", msg);
    }
}
