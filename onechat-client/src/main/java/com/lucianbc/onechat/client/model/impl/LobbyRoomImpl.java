package com.lucianbc.onechat.client.model.impl;

import com.lucianbc.onechat.client.data.UserIdentity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LobbyRoomImpl {
    private List<UserIdentity> connectedUsers = new ArrayList<>();
    private List<UserIdentity> publicList = Collections.unmodifiableList(connectedUsers);

    private void onUserConnected(UserIdentity user) {
        connectedUsers.add(user);
    }

    public List<UserIdentity> getConnectedUsers() {
        return publicList;
    }
}
