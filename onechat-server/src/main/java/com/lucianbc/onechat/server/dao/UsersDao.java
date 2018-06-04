package com.lucianbc.onechat.server.dao;

import com.lucianbc.onechat.data.UserIdentity;

public interface UsersDao {
    void registerUser(UserIdentity ui);
}
