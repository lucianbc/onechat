package com.lucianbc.onechat.client.event;

import com.lucianbc.onechat.client.model.UserIdentity;
import lombok.Data;

@Data
public class UserConnectedEvent {
    private UserIdentity user;
}
