package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.networking.NetworkEndpoint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OpenCommonChatAction implements Action {

    private final NetworkEndpoint networkEndpoint;

    @Override
    public void fire() {
        networkEndpoint.send("/common-register", null);
    }
}
