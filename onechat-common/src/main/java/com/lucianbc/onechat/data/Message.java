package com.lucianbc.onechat.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message<R, U> {
    private R room;
    private U sender;
    private String message;
}
