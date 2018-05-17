package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.model.ChatRoom;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestWritePermissionAction implements Action {
    private final ChatRoom room;
    private final NetworkEndpoint endpoint;

    @Override
    public void fire() {
        endpoint.send("/roomPermission", room.getRoomId());
    }
}
