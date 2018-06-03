package com.lucianbc.onechat.server.dao;

import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.server.UserSessionId;

import java.util.List;

public interface MessagesDao {
    List<Message<String, UserIdentity>> getAllMessages(String roomId);

    void storeMessage(Message<String, UserSessionId> message);
}
