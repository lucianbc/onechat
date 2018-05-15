package com.lucianbc.onechat.client;

import com.lucianbc.onechat.client.action.ActionDispatcher;
import com.lucianbc.onechat.client.application.AppContainer;
import com.lucianbc.onechat.client.application.AppContext;
import com.lucianbc.onechat.client.controller.LoginController;
import com.lucianbc.onechat.client.dao.H2UserIdentityDao;
import com.lucianbc.onechat.client.model.impl.ConnectedUsersImpl;
import com.lucianbc.onechat.client.model.impl.LocalUsersListImpl;
import com.lucianbc.onechat.client.view.SwingAppContainer;
import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.RequestMapper;

public class Main {
    public static void main(String[] args) throws Exception {
        AppContext ctx = loadContext();
        AppContainer container = new SwingAppContainer(ctx);
        ctx.setAppContainer(container);
        container.run();
        container.loadLogin();
    }

    private static AppContext loadContext() throws Exception {
        AppContext ctx = new AppContext();
        ctx.setLocalUsersList(new LocalUsersListImpl(new H2UserIdentityDao()));
        ctx.setConnectedUsers(new ConnectedUsersImpl());
        ctx.setDispatcher(new ActionDispatcher(ctx));
        ctx.setLoginController(new LoginController(ctx.getLocalUsersList(), ctx.getDispatcher()));
        ctx.setRequestMapper(initMappings(ctx));
        return ctx;
    }

    private static RequestMapper initMappings(AppContext ctx) {
        RequestMapper mapper = new RequestMapper();
        mapper.register("/debug", (e) -> {
            System.out.println("Message received on debug queue");
            System.out.println(e);
        }, Object.class);
        mapper.register("/connectedUser", (e) -> ctx.getDispatcher().dispatch(ctx.getDispatcher().factory().userConnected(e)), UserIdentity.class);
        mapper.register("/disconnectedUser", (e) -> ctx.getDispatcher().dispatch(ctx.getDispatcher().factory().userDisconnected(e)), UserIdentity.class);
        mapper.register("/rooms", (e) -> {
            System.out.println("You have been added to room " + e);
        }, String.class);
        return mapper;
    }
}
