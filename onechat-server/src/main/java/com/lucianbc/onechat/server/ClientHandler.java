package com.lucianbc.onechat.server;

import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import com.lucianbc.onechat.networking.RequestMapper;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class ClientHandler implements Runnable {

    private final NetworkEndpoint endpoint;
    private Session userSession;
    private final SessionManager sessionManager;
    private final ChatRoomManager chatRoomManager;

    ClientHandler(Socket socket, SessionManager sessionManager) throws IOException {
        this.sessionManager = sessionManager;
        RequestMapper mapper = initMapper();
        this.endpoint = new NetworkEndpoint(socket, mapper);
        this.chatRoomManager = new ChatRoomManager(sessionManager);
        this.endpoint.onConnectionClosed(this::removeUser);
    }

    @Override
    public void run() {
        endpoint.startListen();
    }

    private RequestMapper initMapper() {
        RequestMapper mapper = new RequestMapper();
        mapper.register("/login", this::loginUser, UserIdentity.class);
        mapper.register("/newRoom", this::openRoom, String.class);

        return mapper;
    }

    private void loginUser(UserIdentity user) {
        this.userSession = new Session(user, endpoint);
        sessionManager.registerSession(userSession);
    }

    private void openRoom(String otherUserId) {
        UserSessionId otherUsi = new UserSessionId(otherUserId);
        if (sessionManager.getSession(otherUsi) == null) return;
        List<UserSessionId> initUsers = new LinkedList<>();
        initUsers.add(userSession.getId());
        initUsers.add(otherUsi);
        chatRoomManager.openRoom(initUsers);
    }

    private void removeUser() {
        sessionManager.dropSession(userSession);
    }
}
