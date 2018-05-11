package com.lucianbc.onechat.server;

import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import com.lucianbc.onechat.networking.RequestMapper;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private NetworkEndpoint endpoint;
    private UserIdentity user;

    ClientHandler(Socket socket) throws IOException {
        RequestMapper mapper = initMapper();
        this.endpoint = new NetworkEndpoint(socket, mapper);
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
        this.user = user;
    }
}
