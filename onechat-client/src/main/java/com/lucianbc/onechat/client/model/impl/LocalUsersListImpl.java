package com.lucianbc.onechat.client.model.impl;

import com.lucianbc.onechat.client.dao.UserIdentityDao;
import com.lucianbc.onechat.client.data.UserIdentity;
import com.lucianbc.onechat.client.model.LocalUsersList;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.sql.SQLException;
import java.util.*;

public class LocalUsersListImpl extends AbstractListModel<UserIdentity> implements LocalUsersList {

    private final UserIdentityDao userIdentityDao;
    private final Vector<UserIdentity> delegate;

    public LocalUsersListImpl(UserIdentityDao userIdentityDao) {
        this.userIdentityDao = userIdentityDao;
        delegate = new Vector<>(getSavedUsers());
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
        delegate.add(userIdentity);
        this.fireIntervalAdded(this, delegate.size() - 1, delegate.size() - 1);
    }

    @Override
    public void removeUser(UserIdentity user) throws SQLException {
        userIdentityDao.removeUser(user);
        int var2 = this.delegate.indexOf(user);
        this.delegate.removeElement(user);
        if (var2 >= 0) {
            this.fireIntervalRemoved(this, var2, var2);
        }
    }

    @Override
    public int getSize() {
        return this.delegate.size();
    }

    @Override
    public UserIdentity getElementAt(int i) {
        return this.delegate.elementAt(i);
    }

    private List<UserIdentity> getSavedUsers() {
        try {
            return this.userIdentityDao.getRegisteredUsers();
        } catch (SQLException ex) {
            return Collections.emptyList();
        }
    }
}
