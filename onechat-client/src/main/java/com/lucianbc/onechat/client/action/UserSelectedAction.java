package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.application.AppContainer;
import com.lucianbc.onechat.client.application.AppContext;
import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UserSelectedAction implements Action {

    private final AppContainer container;
    private final UserIdentity user;
    private final AppContext context;

    UserSelectedAction(AppContainer appContainer, UserIdentity user, AppContext context) {
        this.container = appContainer;
        this.user = user;
        this.context = context;
    }

    @Override
    public void fire() {
        context.setCurrentUser(user);
        container.loadApp();

        context.getRoomsController().setMyUser(user);
        context.getNetworkEndpoint().send("/login", user);
    }
}
