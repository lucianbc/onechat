package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.application.AppContainer;
import com.lucianbc.onechat.client.controller.ChatRoomsController;
import com.lucianbc.onechat.client.model.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class AddedToRoomAction implements Action {

    private static Logger logger = LoggerFactory.getLogger(AddedToRoomAction.class);

    private final AppContainer container;
    private final String roomId;
    private final ChatRoomsController chatRoomsController;

    @Override
    public void fire() {
        logger.info("Added to room");
        ChatRoom room = chatRoomsController.registerRoom(roomId);
        container.openChatRoom(room);
    }
}
