package com.lucianbc.onechat.client.controller;

import com.lucianbc.onechat.client.data.UserIdentity;
import com.lucianbc.onechat.client.model.SavedUsers;
import com.lucianbc.onechat.client.view.LoginScreen;

public class LoginController {
    private final LoginScreen loginScreen;
    private final SavedUsers savedUsers;

    public LoginController(LoginScreen loginScreen, SavedUsers savedUsers) {
        this.loginScreen = loginScreen;
        this.savedUsers = savedUsers;
        setViewActions();
        loginScreen.receiveUsers(savedUsers.getRegisteredUsers());
    }

    private void setViewActions() {
        loginScreen.getActionBinder().setRemoveUserAction(ui -> {
            savedUsers.removeUser(ui);
            loginScreen.onUserRemoved(ui);
        });
        loginScreen.getActionBinder().setAddUserAction(ui -> {
            UserIdentity userIdentity = savedUsers.registerUser(ui);
            loginScreen.onUserAdded(userIdentity);
        });
        loginScreen.getActionBinder().setSelectUserAction(ui -> {
            System.out.println("The user has been selected");
            System.out.println(ui);
        });
    }
}
