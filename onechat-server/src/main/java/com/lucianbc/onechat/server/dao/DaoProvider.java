package com.lucianbc.onechat.server.dao;

public class DaoProvider {

    private static MessagesDao messagesDao;
    private static UsersDao usersDao;

    public static MessagesDao getMessagesDao() {
        if (messagesDao == null) {
            try {
                messagesDao = new DBMessagesDao();
            } catch (Exception e) {
                throw new RuntimeException("Could not connect to db!");
            }
        }

        return messagesDao;
    }

    public static UsersDao getUsersDao() {
        if (usersDao == null) {
            usersDao = new DBUsersDao();
        }
        return usersDao;
    }
}
