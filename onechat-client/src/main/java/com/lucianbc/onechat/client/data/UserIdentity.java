package com.lucianbc.onechat.client.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserIdentity {
    private String id;
    private String username;
}
