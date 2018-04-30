package com.lucianbc.onechat.client.view.impl;

import lombok.Getter;

public class ViewContext {
    @Getter private SwingLoginScreen loginScreen;

    public ViewContext() {
        loginScreen = new SwingLoginScreen();
    }
}
