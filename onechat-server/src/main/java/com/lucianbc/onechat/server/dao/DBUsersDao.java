package com.lucianbc.onechat.server.dao;

import com.lucianbc.onechat.data.UserIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;

class DBUsersDao extends BaseDbDao implements UsersDao {

    private static final Logger logger = LoggerFactory.getLogger(DBUsersDao.class);

    @Override
    public void registerUser(UserIdentity ui) {
        String cmd = "insert into users values (?, ?)";
        try (
                Connection conn = connection();
                PreparedStatement stm = conn.prepareStatement(cmd)
        ) {
            stm.setString(1, ui.getId());
            stm.setString(2, ui.getUsername());
            stm.executeUpdate();
        } catch (Exception ex) {
            logger.error("User not inserted", ex);
        }
    }
}
