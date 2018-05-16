package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StartChatAction implements Action {

    private final UserIdentity targetUser;
    private final NetworkEndpoint networkEndpoint;

    @Override
    public void fire() {
        networkEndpoint.send("/newRoom", targetUser.getId());
    }
}
