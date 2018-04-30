package com.lucianbc.onechat.client.model.impl;

import com.lucianbc.onechat.client.dao.UserIdentityDao;
import com.lucianbc.onechat.client.data.UserIdentity;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SavedUsersImpl {

    private final UserIdentityDao userIdentityDao;

    public SavedUsersImpl(UserIdentityDao userIdentityDao) {
        this.userIdentityDao = userIdentityDao;
    }

    public List<UserIdentity> getRegisteredUsers() {
        try {
            return userIdentityDao.getRegisteredUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void registerUser(String username) {
        UserIdentity userIdentity = new UserIdentity(UUID.randomUUID().toString(), username);
        try {
            userIdentityDao.registerUser(userIdentity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
