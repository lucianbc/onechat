package com.lucianbc.onechat.server;

import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.server.dao.MessagesDao;
import com.lucianbc.onechat.server.dao.DaoProvider;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

class ChatRoom {

    private static final Logger logger = LoggerFactory.getLogger(ChatRoom.class);

    private List<UserSessionId> userSessions = new LinkedList<>();
    @Getter private String roomId;
    private final SessionManager sessionManager;
    private final WritePermissionManager writePermissionManager;
    private final MessagesDao messagesDao;

    ChatRoom(SessionManager sessionManager) {
        this(sessionManager, UUID.randomUUID().toString());
    }

    ChatRoom(SessionManager sessionManager, String roomId) {
        this.sessionManager = sessionManager;
        this.writePermissionManager = new WritePermissionManager(sessionManager, this);
        this.messagesDao = DaoProvider.getMessagesDao();
        this.roomId = roomId;
    }

    void addUserSession(UserSessionId id) {
        Session session = sessionManager.getSession(id);
        if (session == null) return;
        userSessions.add(id);
        session.enterRoom(this);
        messagesDao.getAllMessages(roomId).forEach(session::sendMessage);
    }

    void distributeMessage(Message<String, UserSessionId> message) {
        Session senderSession = sessionManager.getSession(message.getSender());
        if (senderSession == null) return;
        messagesDao.storeMessage(message);
        if (!writePermissionManager.canSend(message.getSender())) return;
        userSessions.forEach(usi -> {
            if (usi.equals(message.getSender())) return;
            Session session = sessionManager.getSession(usi);
            if (session == null) return;
            Message<String, Session> outMsg = new Message<>();
            outMsg.setRoom(message.getRoom());
            outMsg.setSender(senderSession);
            outMsg.setMessage(message.getMessage());
            session.sendMessage(outMsg);
        });
    }

    void handleWriteRequest(UserSessionId userId) {
        writePermissionManager.handleRequest(userId);
    }

    void disconnectSession(Session session) {
        logger.info("User {} left room {}", this.roomId, session.getId());
        userSessions.remove(session.getId());
    }
}
