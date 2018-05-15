package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.model.ConnectedUsers;
import com.lucianbc.onechat.data.UserIdentity;

public class UserDisconnectedAction implements Action {
    private final ConnectedUsers connectedUsers;
    private final UserIdentity userIdentity;

    UserDisconnectedAction(ConnectedUsers connectedUsers, UserIdentity user) {
        this.connectedUsers = connectedUsers;
        this.userIdentity = user;
    }

    @Override
    public void fire() {
        System.out.println("User disconnected");
        this.connectedUsers.removeUser(userIdentity);
    }
}
