package com.lucianbc.onechat.client.service;

import com.lucianbc.onechat.client.event.UserConnectedEvent;
import com.lucianbc.onechat.client.model.UserIdentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyRoom {
    private List<UserIdentity> connectedUsers = new ArrayList<>();
    private List<UserIdentity> publicList = Collections.unmodifiableList(connectedUsers);

    private void onUserConnected(UserConnectedEvent event) {
        connectedUsers.add(event.getUser());
    }

    public List<UserIdentity> getConnectedUsers() {
        return publicList;
    }
}
