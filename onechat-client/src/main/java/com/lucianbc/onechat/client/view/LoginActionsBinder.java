package com.lucianbc.onechat.client.view;

import com.lucianbc.onechat.client.data.UserIdentity;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;

public class LoginActionsBinder {
    @Getter @Setter private Consumer<UserIdentity> removeUserAction;
    @Getter @Setter private Consumer<String> addUserAction;
    @Getter @Setter private Consumer<UserIdentity> selectUserAction;
}
