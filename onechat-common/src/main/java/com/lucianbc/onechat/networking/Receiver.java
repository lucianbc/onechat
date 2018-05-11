package com.lucianbc.onechat.networking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Receiver {
    private static Logger logger = LoggerFactory.getLogger(Receiver.class);
    private Pattern pattern = Pattern.compile(Constants.regex);

    private final RequestMapper requestMapper;

    Receiver(RequestMapper mapper) {
        this.requestMapper = mapper;
    }

    void handleRequest(String request) {
        Matcher matcher = this.pattern.matcher(request);
        if (matcher.matches()) {
            String path = matcher.group(1);
            String payload = matcher.group(2);
            try {
                requestMapper.fire(path, payload);
            } catch (BadPayloadException e) {
                logger.error("Error parsing and firing action", e);
            }
        } else {
            logger.error("Bad request template when parsing incoming request");
        }
    }
}
