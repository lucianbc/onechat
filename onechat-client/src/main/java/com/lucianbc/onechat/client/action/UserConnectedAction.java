package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.model.ConnectedUsers;
import com.lucianbc.onechat.data.UserIdentity;

public class UserConnectedAction implements Action {
    private final ConnectedUsers connectedUsers;
    private final UserIdentity user;

    UserConnectedAction(ConnectedUsers connectedUsers, UserIdentity user) {
        this.connectedUsers = connectedUsers;
        this.user = user;
    }

    @Override
    public void fire() {
        System.out.println("User connected " + user);
        connectedUsers.addUser(user);
    }
}
