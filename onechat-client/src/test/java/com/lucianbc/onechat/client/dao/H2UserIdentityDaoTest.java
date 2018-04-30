package com.lucianbc.onechat.client.dao;

import com.lucianbc.onechat.client.application.OneChatClient;
import com.lucianbc.onechat.client.data.UserIdentity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.*;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@SuppressWarnings({"SqlNoDataSourceInspection", "SqlResolve"})
public class H2UserIdentityDaoTest {
    private static Logger logger = LoggerFactory.getLogger(OneChatClient.class);

    private static final String DB_URL = "jdbc:h2:./onechat";
    private static final String[] DB_FILE_PATHS = {"./onechat.mv.db", "./onechat.trace.db"};
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";


    @After
    @Before
    public void cleanup() {
        logger.info("Cleaning up existing db files");
        for (String path : DB_FILE_PATHS) {
            File file = new File(path);
            if (!file.exists()) {
                logger.info(String.format("File %s does not exists", path));
                continue;
            }
            boolean isDeleted = file.delete();
            String msg = isDeleted ? "Successfully deleted %s" : "Failed deleting %s";
            logger.info(String.format(msg, path));
        }
    }

    @Test
    public void testCreateH2Dao() throws Exception {
        UserIdentityDao dao = new H2UserIdentityDao();
        assertNotNull(dao);
        try (Connection connection = connection()) {
            ResultSet rs = connection.getMetaData().getTables(null, null, "USER_IDENTITY", null);
            assertTrue(rs.next());
        }
    }

    @Test
    public void testInsert() throws Exception {
        UserIdentityDao dao = new H2UserIdentityDao();
        UserIdentity userIdentity = new UserIdentity(UUID.randomUUID().toString(), "testUser");
        dao.registerUser(userIdentity);

        try (Connection connection = connection()) {
            PreparedStatement ps = connection.prepareStatement("select * from user_identity where id = ?");
            ps.setString(1, userIdentity.getId());
            ResultSet rs = ps.executeQuery();

            assertTrue(rs.next());
        }
    }

    @Test
    public void testQuery() throws Exception {
        UserIdentityDao dao = new H2UserIdentityDao();
        List<UserIdentity> samples = populateTable();

        List<UserIdentity> userIdentities = dao.getRegisteredUsers();
        assertEquals(samples, userIdentities);
    }

    @Test
    public void testDelete() throws Exception {
        UserIdentityDao dao = new H2UserIdentityDao();
        List<UserIdentity> samples = populateTable();
        for (UserIdentity ui : samples) {
            dao.removeUser(ui);
        }

        for (UserIdentity ui : samples) {
            try (Connection connection = connection()) {
                PreparedStatement ps = connection.prepareStatement("select * from user_identity where id = ?");
                ps.setString(1, ui.getId());
                ResultSet rs = ps.executeQuery();

                assertFalse(rs.next());
            }
        }
    }

    private List<UserIdentity> populateTable() throws Exception {
        List<UserIdentity> samples = Collections.singletonList(new UserIdentity(UUID.randomUUID().toString(), "testUser"));

        try (Connection connection = connection()) {
            PreparedStatement ps = connection.prepareStatement("insert into USER_IDENTITY values(?, ?);");
            for(UserIdentity ui : samples) {
                ps.setString(1, ui.getId());
                ps.setString(2, ui.getUsername());
                ps.executeUpdate();
            }
        }
        return samples;
    }

    private Connection connection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}