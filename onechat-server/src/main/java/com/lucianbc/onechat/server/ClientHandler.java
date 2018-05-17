package com.lucianbc.onechat.server;

import com.lucianbc.onechat.data.Message;
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

    ClientHandler(Socket socket, SessionManager sessionManager, ChatRoomManager chatRoomManager) throws IOException {
        this.sessionManager = sessionManager;
        RequestMapper mapper = initMapper();
        this.endpoint = new NetworkEndpoint(socket, mapper);
        this.chatRoomManager = chatRoomManager;
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
        mapper.register("/messages", this::handleMessage, Message.class);
        mapper.register("/roomPermission", this::handlePermissionRequest, String.class);
        return mapper;
    }

    private void handlePermissionRequest(String roomId) {
        System.out.println("Handling a permission ask");
        chatRoomManager.handleWriteRequest(roomId, userSession.getId());
    }

    private void handleMessage(Message<String, String> t) {
        Message<String, UserSessionId> msg = new Message<>();
        msg.setRoom(t.getRoom());
        msg.setMessage(t.getMessage());
        msg.setSender(new UserSessionId(t.getSender()));
        this.chatRoomManager.handleMessage(msg);
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
