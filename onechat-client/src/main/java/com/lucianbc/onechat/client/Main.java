package com.lucianbc.onechat.client;

import com.lucianbc.onechat.client.action.ActionDispatcher;
import com.lucianbc.onechat.client.application.AppContainer;
import com.lucianbc.onechat.client.application.AppContext;
import com.lucianbc.onechat.client.controller.LoginController;
import com.lucianbc.onechat.client.dao.H2UserIdentityDao;
import com.lucianbc.onechat.client.model.impl.LocalUsersListImpl;
import com.lucianbc.onechat.client.view.SwingAppContainer;
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
        ctx.setDispatcher(new ActionDispatcher(ctx));
        ctx.setLoginController(new LoginController(ctx.getLocalUsersList(), ctx.getDispatcher()));
        ctx.setRequestMapper(initMappings());
        return ctx;
    }

    private static RequestMapper initMappings() {
        RequestMapper mapper = new RequestMapper();
        mapper.register("/debug", (e) -> {
            System.out.println("Message received on debug queue");
            System.out.println(e);
        }, Object.class);
        return mapper;
    }
}
