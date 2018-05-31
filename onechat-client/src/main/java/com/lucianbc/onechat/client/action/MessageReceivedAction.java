package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.controller.ChatRoomsController;
import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.data.UserIdentity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageReceivedAction implements Action {
    private final ChatRoomsController roomsController;
    private final Message<String, UserIdentity> m;

    @Override
    public void fire() {
        roomsController.handleMessage(m);
    }
}
