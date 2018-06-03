package com.lucianbc.onechat.server.dao;

public class MessagesDaoProvider {

    private static MessagesDao messagesDao;

    public static MessagesDao getInstance() {
        if (messagesDao == null) {
            try {
                messagesDao = new DBMessagesDao();
            } catch (Exception e) {
                throw new RuntimeException("Could not connect to db!");
            }
        }

        return messagesDao;
    }
}
