package com.lucianbc.onechat.client.application;

import com.lucianbc.onechat.client.action.ActionDispatcher;
import com.lucianbc.onechat.client.controller.LoginController;
import com.lucianbc.onechat.client.model.impl.LocalUsersListImpl;
import lombok.Data;

@Data
public class AppContext {
    private LocalUsersListImpl localUsersList;
    private LoginController loginController;
    private AppContainer appContainer;
    private ActionDispatcher dispatcher;
}
