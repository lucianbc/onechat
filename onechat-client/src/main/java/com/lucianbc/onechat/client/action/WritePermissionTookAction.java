package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.controller.ChatRoomsController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WritePermissionTookAction implements Action {
    private final ChatRoomsController roomsController;
    private final String roomId;

    @Override
    public void fire() {
        roomsController.removeWritePermission(roomId);
    }
}
