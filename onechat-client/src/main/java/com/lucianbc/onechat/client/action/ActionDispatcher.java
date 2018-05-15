package com.lucianbc.onechat.client.action;

import com.lucianbc.onechat.client.application.AppContext;
import com.lucianbc.onechat.data.UserIdentity;

public class ActionDispatcher {
    private final AppContext context;
    private final Factory factory;

    public ActionDispatcher(AppContext context) {
        this.context = context;
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

        public UserSelectedAction userSelected(UserIdentity user) {
            return new UserSelectedAction(context.getAppContainer(), user, context);
        }

        public UserConnectedAction userConnected(UserIdentity user) {
            return new UserConnectedAction(context.getConnectedUsers(), user);
        }

        public UserDisconnectedAction userDisconnected(UserIdentity user) {
            return new UserDisconnectedAction(context.getConnectedUsers(), user);
        }
    }
}
