package com.lucianbc.onechat.server;

import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import com.lucianbc.onechat.networking.RequestMapper;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private final NetworkEndpoint endpoint;
    private Session userSession;
    private final SessionManager sessionManager;

    ClientHandler(Socket socket, SessionManager sessionManager) throws IOException {
        this.sessionManager = sessionManager;
        RequestMapper mapper = initMapper();
        this.endpoint = new NetworkEndpoint(socket, mapper);
        this.endpoint.onConnectionClosed(this::removeUser);
    }

    @Override
    public void run() {
        endpoint.startListen();
    }

    private RequestMapper initMapper() {
        RequestMapper mapper = new RequestMapper();
        mapper.register("/login", this::loginUser, UserIdentity.class);

        return mapper;
    }

    private void loginUser(UserIdentity user) {
        this.userSession = new Session(user, endpoint);
        sessionManager.registerSession(userSession);
    }

    private void removeUser() {
        sessionManager.dropSession(userSession);
    }
}
