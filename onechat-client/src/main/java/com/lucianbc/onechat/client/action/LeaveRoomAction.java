package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.model.ChatRoom;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LeaveRoomAction implements Action {

    private final NetworkEndpoint networkEndpoint;
    private final ChatRoom room;

    @Override
    public void fire() {
        networkEndpoint.send("/leaveRoom", room.getRoomId());
    }
}
