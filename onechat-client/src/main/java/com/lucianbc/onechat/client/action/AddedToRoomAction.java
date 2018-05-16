package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.application.AppContainer;
import com.lucianbc.onechat.client.controller.ChatRoomsController;
import com.lucianbc.onechat.client.model.ChatRoom;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddedToRoomAction implements Action {

    private final AppContainer container;
    private final String roomId;
    private final ChatRoomsController chatRoomsController;

    @Override
    public void fire() {
        System.out.println("Added to room");
        ChatRoom room = chatRoomsController.registerRoom(roomId);
        container.openChatRoom(room);
    }
}
