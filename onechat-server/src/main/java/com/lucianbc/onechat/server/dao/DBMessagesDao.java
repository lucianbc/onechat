package com.lucianbc.onechat.server.dao;

import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.server.UserSessionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class DBMessagesDao extends BaseDbDao implements MessagesDao {

    private static final Logger logger = LoggerFactory.getLogger(DBMessagesDao.class);


    DBMessagesDao() throws SQLException, ClassNotFoundException {
        init();
    }

    @Override
    public List<Message<String, UserIdentity>> getAllMessages(String roomId) {
        String cmd = "select * from messages left join users on messages.user_id = users.id where room_id = ? order by timestamp";
        List<Message<String, UserIdentity>> l = new LinkedList<>();
        try (
                Connection conn = connection();
                PreparedStatement stm = conn.prepareStatement(cmd);
            ) {
            stm.setString(1, roomId);
            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    Message<String, UserIdentity> msg = new Message<>();
                    msg.setMessage(rs.getString("message"));
                    String username = rs.getString("username");
                    UserIdentity userIdentity = new UserIdentity(
                            rs.getString("user_id"),
                            username == null ? "anonymous" : username);
                    msg.setSender(userIdentity);
                    msg.setRoom(rs.getString("room_id"));
                    msg.setTimestamp(rs.getTimestamp("timestamp"));
                    l.add(msg);
                }
            }

        } catch (Exception ex) {
            logger.error("Message query failed", ex);
        }

        return l;
    }


//    Boaca Lucian - 10


    @Override
    public void storeMessage(Message<String, UserSessionId> message) {
        String cmd = "insert into messages (user_id, room_id, message, timestamp) values (?, ?, ?, ?)";
        try (
                Connection conn = connection();
                PreparedStatement stm = conn.prepareStatement(cmd)
        ) {
            stm.setString(1, message.getSender().getId());
            stm.setString(2, message.getRoom());
            stm.setString(3, message.getMessage());
            stm.setTimestamp(4, new Timestamp(message.getTimestamp().getTime()));
            stm.executeUpdate();
        } catch (Exception ex) {
            logger.error("Message lost", ex);
        }
    }

    private void init() throws SQLException {
        try (
                Connection conn = connection();
                Statement stm = conn.createStatement()
        ) {
            List<String> initCommands = initDbCommands();
            for (String cmd : initCommands) {
                stm.executeUpdate(cmd);
            }
        }
    }


    private List<String> initDbCommands() {
        String initMessagesTable = "CREATE TABLE IF NOT EXISTS messages (\n" +
                "  id INT NOT NULL AUTO_INCREMENT,\n" +
                "  user_id VARCHAR(60) NOT NULL,\n" +
                "  room_id VARCHAR(60) NOT NULL,\n" +
                "  message VARCHAR(45) NULL,\n" +
                "  timestamp DATETIME NOT NULL,\n" +
                "  PRIMARY KEY (id));\n";
        String initUsersTable = "create table if not exists users ( i" +
                "d varchar(60) not null, username varchar(50) not null, " +
                "PRIMARY KEY (id));";
        return Arrays.asList(initMessagesTable, initUsersTable);
    }
}
