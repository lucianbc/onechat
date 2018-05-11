package com.lucianbc.onechat.server;

import com.lucianbc.onechat.data.UserIdentity;
import com.lucianbc.onechat.networking.NetworkEndpoint;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Session {
    private UserIdentity user;
    private NetworkEndpoint networkEndpoint;
}
