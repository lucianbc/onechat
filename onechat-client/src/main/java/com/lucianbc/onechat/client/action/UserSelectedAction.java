package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.application.AppContainer;
import com.lucianbc.onechat.client.data.UserIdentity;

public class UserSelectedAction implements Action {

    private final AppContainer container;
    private final UserIdentity user;

    UserSelectedAction(AppContainer appContainer, UserIdentity user) {
        this.container = appContainer;
        this.user = user;
    }

    @Override
    public void fire() {
        container.loadApp();
    }
}
