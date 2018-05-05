package com.lucianbc.onechat.client.controller;

import com.lucianbc.onechat.client.action.ActionDispatcher;
import com.lucianbc.onechat.client.data.UserIdentity;
import com.lucianbc.onechat.client.model.LocalUsersList;

import java.sql.SQLException;

public class LoginController {

    private final LocalUsersList localUsersList;
    private final ActionDispatcher dispatcher;

    public LoginController(LocalUsersList localUsersList, ActionDispatcher dispatcher) {
        this.localUsersList = localUsersList;
        this.dispatcher = dispatcher;
    }

    public void removeUser(UserIdentity ui) throws SQLException {
        localUsersList.removeUser(ui);
    }

    public void addUser(String username) throws SQLException {
        localUsersList.registerUser(username);
    }

    public void selectUser(UserIdentity selectedValue) {
        if (selectedValue != null)
            dispatcher.dispatch(dispatcher.factory().userSelected(selectedValue));
    }
}
