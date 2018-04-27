package com.lucianbc.onechat.client.dao;

import com.lucianbc.onechat.client.model.UserIdentity;

import java.sql.SQLException;
import java.util.List;

public interface UserIdentityDao {
    List<UserIdentity> getRegisteredUsers() throws SQLException;
    void registerUser(UserIdentity user) throws SQLException;
}
