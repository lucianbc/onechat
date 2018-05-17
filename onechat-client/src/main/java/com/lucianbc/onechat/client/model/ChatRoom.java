package com.lucianbc.onechat.client.model;

import com.lucianbc.onechat.client.view.ChatPane;
import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.data.UserIdentity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChatRoom {
    @Getter private final String roomId;
    private ChatPane chatPane;

    public void bindView(ChatPane chatPane) {
        this.chatPane = chatPane;
    }

    public void receiveMessage(Message<String, UserIdentity> message) {
        if (chatPane != null) {
            chatPane.addMessage(message.getMessage(), message.getSender().getUsername());
        }
    }

    public void grantWritePermission() {
        chatPane.enableWrite();
    }

    public void removeWritePermission() {
        chatPane.disableWrite();
    }
}
