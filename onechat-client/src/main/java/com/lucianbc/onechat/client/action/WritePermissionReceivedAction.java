package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.controller.ChatRoomsController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WritePermissionReceivedAction implements Action {
    private final ChatRoomsController chatRoomsController;
    private final String roomId;


    @Override
    public void fire() {
        chatRoomsController.grantWritePermission(roomId);
    }
}
