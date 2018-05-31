package com.lucianbc.onechat.networking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Sender {
    Logger logger = LoggerFactory.getLogger(Sender.class);

    private ObjectMapper mapper = ObjectMapperProvider.getInstance();

    String toRequest(String path, Object payload) throws BadPayloadException {
        try {
            String serializedPayload = mapper.writeValueAsString(payload);
            return String.format(Constants.TEMPLATE, path, serializedPayload);
        } catch (JsonProcessingException e) {
            throw new BadPayloadException(e.getMessage());
        }
    }
}
