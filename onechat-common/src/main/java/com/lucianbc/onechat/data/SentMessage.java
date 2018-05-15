package com.lucianbc.onechat.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SentMessage<RID, UID> {
    private RID roomId;
    private UID senderId;
    private String message;
}
