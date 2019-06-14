package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCreatedAction implements Action {
    private final NetworkEndpoint networkEndpoint;
    private final UserIdentity ui;

    @Override
    public void fire() {
        networkEndpoint.send("/newUser", ui);
    }
}
