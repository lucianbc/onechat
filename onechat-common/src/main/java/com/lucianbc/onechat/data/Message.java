package com.lucianbc.onechat.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class Message<R, U> {
    private R room;
    private U sender;
    private String message;
    private Date timestamp = new Date();

    public Message(R room, U sender, String message) {
        this.room = room;
        this.sender = sender;
        this.message = message;
    }
}
