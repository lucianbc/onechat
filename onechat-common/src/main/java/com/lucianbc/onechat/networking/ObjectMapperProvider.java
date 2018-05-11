package com.lucianbc.onechat.networking;

import com.fasterxml.jackson.databind.ObjectMapper;

class ObjectMapperProvider {
    private static ObjectMapper mapper;

    static ObjectMapper getInstance() {
        if (mapper == null) mapper = new ObjectMapper();
        return mapper;
    }
}
