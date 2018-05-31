package com.lucianbc.onechat.client.dao;

import com.lucianbc.onechat.data.UserIdentity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
public class H2UserIdentityDao implements UserIdentityDao {

    private static final String CONNECTION_URL = "jdbc:h2:./onechat";
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    public H2UserIdentityDao() throws Exception {
        init();
    }

    @Override
    public List<UserIdentity> getRegisteredUsers() throws SQLException {
        String cmd = "select * from user_identity;";
        List<UserIdentity> identities = new ArrayList<>();
        try (
            Connection conn = connection();
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(cmd)
        ) {
            while (rs.next()) {
                String id = rs.getString(1);
                String username = rs.getString(2);
                identities.add(new UserIdentity(id, username));
            }
        }
        return identities;
    }

    @Override
    public void registerUser(UserIdentity user) throws SQLException {
        String cmd = "insert into user_identity values (?, ?)";
        try (
                Connection conn = connection();
                PreparedStatement stm = conn.prepareStatement(cmd)
        ) {
            stm.setString(1, user.getId());
            stm.setString(2, user.getUsername());
            stm.executeUpdate();
        }
    }

    @Override
    public void removeUser(UserIdentity user) throws SQLException {
        String cmd = "delete from user_identity where id = ?";
        try (
                Connection conn = connection();
                PreparedStatement stm = conn.prepareStatement(cmd)
        ) {
            stm.setString(1, user.getId());
            stm.executeUpdate();
        }
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, DB_USER, DB_PASSWORD);
    }

    private void init() throws SQLException, ClassNotFoundException {
        Class.forName(DB_DRIVER);

        try (
                Connection conn = connection();
                Statement stm = conn.createStatement()
        ) {
            stm.executeUpdate("create table if not exists user_identity( id varchar(60) not null, username varchar(50) not null, PRIMARY KEY (id));");
        }
    }
}
