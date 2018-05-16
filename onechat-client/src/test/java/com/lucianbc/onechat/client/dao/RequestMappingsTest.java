package com.lucianbc.onechat.client.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.RequestMapper;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class RequestMappingsTest {
    @Test
    public void testMessageMapping() throws Exception {
        RequestMapper mapper = new RequestMapper();
        Message<String, UserIdentity> message = new Message<>("room-id", new UserIdentity("user-id", "username"), "mesage");
        String serialized = (new ObjectMapper()).writeValueAsString(message);

        List<Class> messageClasses = new LinkedList<>();
        messageClasses.add(Message.class);
        messageClasses.add(String.class);
        messageClasses.add(UserIdentity.class);

        mapper.register("/test", this::testResp, messageClasses);

        mapper.fire("/test", serialized);
    }

    private void testResp(Message<String, UserIdentity> msg) {
        System.out.println(msg);
    }
}
