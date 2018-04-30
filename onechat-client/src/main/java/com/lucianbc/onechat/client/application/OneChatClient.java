package com.lucianbc.onechat.client.application;

import com.lucianbc.onechat.client.controller.LoginController;
import com.lucianbc.onechat.client.dao.H2UserIdentityDao;
import com.lucianbc.onechat.client.dao.UserIdentityDao;
import com.lucianbc.onechat.client.model.impl.SavedUsersImpl;
import com.lucianbc.onechat.client.view.LoginScreen;
import com.lucianbc.onechat.client.view.impl.CliLoginScreen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OneChatClient {
    private static Logger logger = LoggerFactory.getLogger(OneChatClient.class);
    public void run() throws Exception {
        LoginScreen loginScreen = new CliLoginScreen();
        UserIdentityDao dao = new H2UserIdentityDao();
        LoginController controller = new LoginController(loginScreen, new SavedUsersImpl(new H2UserIdentityDao()));
    }
}
