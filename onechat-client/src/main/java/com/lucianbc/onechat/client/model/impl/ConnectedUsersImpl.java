package com.lucianbc.onechat.client.model.impl;

import com.lucianbc.onechat.client.model.ConnectedUsers;
import com.lucianbc.onechat.data.UserIdentity;

import java.util.List;

public class ConnectedUsersImpl extends MyAbstractListModel<UserIdentity> implements ConnectedUsers {
    @Override
    public List<UserIdentity> getConnectedUsers() {
        return delegate;
    }

    @Override
    public void addUser(UserIdentity user) {
        add(user);
    }

    @Override
    public void removeUser(UserIdentity user) {
        remove(user);
    }
}
