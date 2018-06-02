package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.application.AppContext;
import com.lucianbc.onechat.client.model.ChatRoom;
import com.lucianbc.onechat.data.Message;
import com.lucianbc.onechat.data.UserIdentity;

public class ActionDispatcher {
    private final Factory factory;

    public ActionDispatcher(AppContext context) {
        this.factory = new Factory(context);
    }

    public void dispatch(Action a) {
        a.fire();
    }

    public Factory factory() {
        return factory;
    }

    public class Factory {
        private AppContext context;

        Factory(AppContext context) {
            this.context = context;
        }

        public Action userSelected(UserIdentity user) {
            return new UserSelectedAction(context.getAppContainer(), user, context);
        }

        public Action userConnected(UserIdentity user) {
            return new UserConnectedAction(context.getConnectedUsers(), user);
        }

        public Action userDisconnected(UserIdentity user) {
            return new UserDisconnectedAction(context.getConnectedUsers(), user);
        }

        public Action startChat(UserIdentity targetUser) {
            return new StartChatAction(targetUser, context.getNetworkEndpoint());
        }

        public Action sendMessage(ChatRoom chatRoom, String message) {
            return new SendMessageAction(context.getCurrentUser(), chatRoom, message, context.getNetworkEndpoint());
        }

        public Action addedToRoom(String roomId) {
            return new AddedToRoomAction(context.getAppContainer(), roomId, context.getRoomsController());
        }

        public Action messageReceived(Message<String, UserIdentity> m) {
            return new MessageReceivedAction(context.getRoomsController(), m);
        }

        public Action accessReceived(String roomId) {
            return new WritePermissionReceivedAction(context.getRoomsController(), roomId);
        }

        public Action accessTook(String roomId) {
            return new WritePermissionTookAction(context.getRoomsController(), roomId);
        }

        public Action requestWriteAccess(ChatRoom room) {
            return new RequestWritePermissionAction(room, context.getNetworkEndpoint());
        }

        public Action openCommonChat() {
            return new OpenCommonChatAction(context.getNetworkEndpoint());
        }

        public Action leaveRoom(ChatRoom room) {
            return new LeaveRoomAction(context.getNetworkEndpoint(), room);
        }
    }
}
