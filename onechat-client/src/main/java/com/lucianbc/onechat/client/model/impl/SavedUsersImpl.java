package com.lucianbc.onechat.client.model.impl;

import com.lucianbc.onechat.client.dao.UserIdentityDao;
import com.lucianbc.onechat.client.data.UserIdentity;
import com.lucianbc.onechat.client.model.SavedUsers;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SavedUsersImpl implements SavedUsers {

    private final UserIdentityDao userIdentityDao;

    public SavedUsersImpl(UserIdentityDao userIdentityDao) {
        this.userIdentityDao = userIdentityDao;
    }

    @Override
    public List<UserIdentity> getRegisteredUsers() {
        try {
            return userIdentityDao.getRegisteredUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void removeUser(UserIdentity user) {
        try {
            userIdentityDao.removeUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public UserIdentity registerUser(String username) {
        UserIdentity userIdentity = new UserIdentity(UUID.randomUUID().toString(), username);
        try {
            userIdentityDao.registerUser(userIdentity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userIdentity;
    }
}
