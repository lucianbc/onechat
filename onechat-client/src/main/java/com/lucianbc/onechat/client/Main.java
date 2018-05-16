package com.lucianbc.onechat.client;

import com.lucianbc.onechat.client.action.ActionDispatcher;
import com.lucianbc.onechat.client.application.AppContainer;
import com.lucianbc.onechat.client.application.AppContext;
import com.lucianbc.onechat.client.controller.ChatRoomsController;
import com.lucianbc.onechat.client.controller.LoginController;
import com.lucianbc.onechat.client.dao.H2UserIdentityDao;
import com.lucianbc.onechat.client.model.impl.ConnectedUsersImpl;
import com.lucianbc.onechat.client.model.impl.LocalUsersListImpl;
import com.lucianbc.onechat.client.view.SwingAppContainer;
import com.lucianbc.onechat.data.Message;
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
        ctx.setRoomsController(new ChatRoomsController());
        ctx.setRequestMapper(initMappings(ctx.getDispatcher()));
        return ctx;
    }

    private static RequestMapper initMappings(ActionDispatcher d) {
        ActionDispatcher.Factory f = d.factory();
        RequestMapper mapper = new RequestMapper();

        mapper.register("/connectedUser", (e) -> d.dispatch(f.userConnected(e)), UserIdentity.class);
        mapper.register("/disconnectedUser", (e) -> d.dispatch(f.userDisconnected(e)), UserIdentity.class);
        mapper.register("/rooms", (e) -> d.dispatch(f.addedToRoom(e)), String.class);
        mapper.register("/incomingMessages", (e) -> d.dispatch(f.messageReceived(e)), Message.class);

        return mapper;
    }
}
