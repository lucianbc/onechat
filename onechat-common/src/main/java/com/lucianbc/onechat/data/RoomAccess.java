package com.lucianbc.onechat.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomAccess {
    private String roomId;
    private boolean hasAccess;
}
