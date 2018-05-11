package com.lucianbc.onechat.networking;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class RequestMapper {

    private Map<String, MappingRecord> actions = new HashMap<>();
    private ObjectMapper mapper = ObjectMapperProvider.getInstance();


    @SuppressWarnings("unchecked")
    void fire(String path, String serializedArg) throws BadPayloadException {
        MappingRecord record = actions.get(path);
        if (record == null) return;
        try {
            Object arg = mapper.readValue(serializedArg, record.getParamType());
            record.getAction().accept(arg);
        } catch (IOException e) {
            throw new BadPayloadException(e.getMessage());
        }
    }

    public <T> void register(String path, Consumer<T> action, Class<T> argType) {
        MappingRecord record = new MappingRecord(action, argType);
        actions.put(path, record);
    }

    @AllArgsConstructor
    private class MappingRecord {
        @Getter
        private Consumer action;
        @Getter private Class paramType;
    }
}
