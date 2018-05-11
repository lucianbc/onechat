package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.application.AppContainer;
import com.lucianbc.onechat.client.application.AppContext;
import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class UserSelectedAction implements Action {

    private static final Logger logger = LoggerFactory.getLogger(UserSelectedAction.class);

    private static final String HOST = "localhost";
    private static final int PORT = 5000;

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
        container.loadApp();
        try {
            NetworkEndpoint ne = new NetworkEndpoint(HOST, PORT, context.getRequestMapper());
            ne.startListen();
            context.setNetworkEndpoint(ne);
        } catch (IOException e) {
            logger.error("Failed to create network endpoint", e);
//            TODO: Implement a mechanism on the getter of the endpoint to alarm the user if the app is not connected
        }
        context.getNetworkEndpoint().send("/login", user);
    }
}
