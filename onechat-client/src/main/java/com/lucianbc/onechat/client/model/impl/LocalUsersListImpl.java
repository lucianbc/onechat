package com.lucianbc.onechat.client.model.impl;

import com.lucianbc.onechat.client.dao.UserIdentityDao;
import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.client.model.LocalUsersList;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.sql.SQLException;
import java.util.*;

public class LocalUsersListImpl extends MyAbstractListModel<UserIdentity> implements LocalUsersList {

    private final UserIdentityDao userIdentityDao;

    public LocalUsersListImpl(UserIdentityDao userIdentityDao) {
        super();
        this.userIdentityDao = userIdentityDao;
        this.delegate.addAll(getSavedUsers());
        this.fireIntervalAdded(this, 0, delegate.size() - 1);
    }

    @Override
    public List<UserIdentity> getRegisteredUsers() {
        return delegate;
    }

    @Override
    public void registerUser(String username) throws SQLException {
        UserIdentity userIdentity = new UserIdentity(UUID.randomUUID().toString(), username);
        userIdentityDao.registerUser(userIdentity);
        add(userIdentity);
    }

    @Override
    public void removeUser(UserIdentity user) throws SQLException {
        userIdentityDao.removeUser(user);
        remove(user);
    }

    private List<UserIdentity> getSavedUsers() {
        try {
            return this.userIdentityDao.getRegisteredUsers();
        } catch (SQLException ex) {
            return Collections.emptyList();
        }
    }
}
