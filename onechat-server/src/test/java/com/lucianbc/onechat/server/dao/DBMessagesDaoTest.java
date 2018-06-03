package com.lucianbc.onechat.server.dao;

import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.server.UserSessionId;
import org.junit.Test;

import java.util.List;

public class DBMessagesDaoTest {

    @Test
    public void testConnect() throws Exception {
        MessagesDao dao = new DBMessagesDao();
        Message<String, UserSessionId> msg = new Message<>();
        msg.setRoom("roomId");
        msg.setSender(new UserSessionId("userId"));
        msg.setMessage("asd fgh");
        dao.storeMessage(msg);
//        List l = dao.getAllMessages("roomId");
//        System.out.println(l);
    }
}