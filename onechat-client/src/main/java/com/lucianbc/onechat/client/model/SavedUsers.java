package com.lucianbc.onechat.client.model;

import com.lucianbc.onechat.client.data.UserIdentity;

import java.util.List;

public interface SavedUsers {
    UserIdentity registerUser(String username);
    List<UserIdentity> getRegisteredUsers();
    void removeUser(UserIdentity username);
}
