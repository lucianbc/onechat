package com.lucianbc.onechat.client.model;

import com.lucianbc.onechat.client.data.UserIdentity;

import java.util.List;

public interface LobbyRoom {
    void addUser(UserIdentity user);
    void removeUser(UserIdentity user);
    List<UserIdentity> getAllUsers();
}
