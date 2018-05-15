package com.lucianbc.onechat.client.model;

import com.lucianbc.onechat.data.UserIdentity;

import java.util.List;

public interface ConnectedUsers {
    List<UserIdentity> getConnectedUsers();
    void addUser(UserIdentity user);
    void removeUser(UserIdentity user);
}
