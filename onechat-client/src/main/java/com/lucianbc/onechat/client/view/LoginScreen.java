package com.lucianbc.onechat.client.view;

import com.lucianbc.onechat.client.data.UserIdentity;

import java.util.List;

public interface LoginScreen {
    // actions sent to the controller using the binder
    void removeUser(UserIdentity user);
    void addUser(String username);
    void selectUser(UserIdentity user);

    // actions performed when the command is received from the controller
    void receiveUsers(List<UserIdentity> users);
    void onUserRemoved(UserIdentity user);
    void onUserAdded(UserIdentity user);

    // Actions binder to controller
    LoginActionsBinder getActionBinder();
}
