package com.lucianbc.onechat.server;

import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Session {
    private UserIdentity user;
    private NetworkEndpoint networkEndpoint;

    public void notifyAboutConnected(Session userSession) {
        networkEndpoint.send("/connectedUser", userSession.user);
    }

    public void notifyAboutDisconnected(Session userSession) {
        networkEndpoint.send("/disconnectedUser", userSession.user);
    }
}
