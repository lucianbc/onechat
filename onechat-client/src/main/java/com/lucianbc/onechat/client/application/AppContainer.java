package com.lucianbc.onechat.client.application;

import com.lucianbc.onechat.client.model.ChatRoom;

public interface AppContainer {
    void loadLogin();
    void loadApp();
    void openChatRoom(ChatRoom room);
    void run();
}
