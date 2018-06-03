package com.lucianbc.onechat.client;

import com.lucianbc.onechat.client.action.Action;
import com.lucianbc.onechat.client.action.ActionDispatcher;
import com.lucianbc.onechat.client.action.UserSelectedAction;
import com.lucianbc.onechat.client.application.AppContainer;
import com.lucianbc.onechat.client.application.AppContext;
import com.lucianbc.onechat.client.controller.ChatRoomsController;
import com.lucianbc.onechat.client.controller.LoginController;
import com.lucianbc.onechat.client.dao.H2UserIdentityDao;
import com.lucianbc.onechat.client.model.impl.ConnectedUsersImpl;
import com.lucianbc.onechat.client.model.impl.LocalUsersListImpl;
import com.lucianbc.onechat.client.view.SwingAppContainer;
import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.data.RoomAccess;
import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import com.lucianbc.onechat.networking.RequestMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

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
        ctx.setRoomsController(new ChatRoomsController(ctx.getDispatcher()));
        ctx.setRequestMapper(initMappings(ctx.getDispatcher()));
        initNetworkEndpoint(ctx.getRequestMapper(), ctx);
        return ctx;
    }

    private static void initNetworkEndpoint(RequestMapper mapper, AppContext context) {
        final String HOST = "localhost";
        final int PORT = 5000;

        try {
            NetworkEndpoint ne = new NetworkEndpoint(HOST, PORT, mapper);
            ne.startListen();
            context.setNetworkEndpoint(ne);
        } catch (IOException e) {
            logger.error("Failed to create network endpoint", e);
//            TODO: Implement a mechanism on the getter of the endpoint to alarm the user if the app is not connected
        }
    }

    // Boaca Lucian
    // 9




    @SuppressWarnings("unchecked")
    private static RequestMapper initMappings(ActionDispatcher d) {
        ActionDispatcher.Factory f = d.factory();
        RequestMapper mapper = new RequestMapper();

        mapper.register("/connectedUser", e -> d.dispatch(f.userConnected(e)), UserIdentity.class);
        mapper.register("/disconnectedUser", e -> d.dispatch(f.userDisconnected(e)), UserIdentity.class);
        mapper.register("/rooms", e -> d.dispatch(f.addedToRoom(e)), String.class);


        List<Class> messageClasses = new LinkedList<>();
        messageClasses.add(Message.class);
        messageClasses.add(String.class);
        messageClasses.add(UserIdentity.class);
        mapper.register("/incomingMessages", e -> d.dispatch(f.messageReceived((Message<String, UserIdentity>)e)), messageClasses);

        mapper.register("/writeAccess", e -> {
            Action action = e.isHasAccess() ? f.accessReceived(e.getRoomId()) : f.accessTook(e.getRoomId());
            d.dispatch(action);
        }, RoomAccess.class);

        return mapper;
    }
}
