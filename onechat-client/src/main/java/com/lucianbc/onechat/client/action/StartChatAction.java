package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StartChatAction implements Action {

    private final UserIdentity starterUser;
    private final UserIdentity targetUser;
    private final NetworkEndpoint networkEndpoint;

    @Override
    public void fire() {
        System.out.println(String.format("%s wants to chat with %s", starterUser.getUsername(), targetUser.getUsername()));
        networkEndpoint.send("/newRoom", targetUser.getId());
    }
}
