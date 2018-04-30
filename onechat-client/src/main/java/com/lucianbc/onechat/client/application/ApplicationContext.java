package com.lucianbc.onechat.client.application;

import com.lucianbc.onechat.client.controller.LoginController;
import com.lucianbc.onechat.client.dao.H2UserIdentityDao;
import com.lucianbc.onechat.client.dao.UserIdentityDao;
import com.lucianbc.onechat.client.model.SavedUsers;
import com.lucianbc.onechat.client.model.impl.SavedUsersImpl;
import com.lucianbc.onechat.client.view.impl.ViewContext;
import lombok.Getter;

class ApplicationContext {
    @Getter private ViewContext viewContext;
    @Getter private LoginController loginController;
    @Getter private SavedUsers savedUsers;
    @Getter private UserIdentityDao userIdentityDao;

    private ApplicationContext() {}

    private void init() throws Exception {
        viewContext = new ViewContext();
        userIdentityDao = new H2UserIdentityDao();
        savedUsers = new SavedUsersImpl(userIdentityDao);
        loginController = new LoginController(viewContext.getLoginScreen() ,savedUsers);
    }

    static ApplicationContext getInsance() throws Exception {
        ApplicationContext context = new ApplicationContext();
        context.init();
        return context;
    }
}
