package com.lucianbc.onechat.client.model;

import com.lucianbc.onechat.client.data.UserIdentity;

import java.sql.SQLException;
import java.util.List;

public interface LocalUsersList {
    List<UserIdentity> getRegisteredUsers() throws SQLException;
    void registerUser(String username) throws SQLException;
    void removeUser(UserIdentity user) throws SQLException;
}
