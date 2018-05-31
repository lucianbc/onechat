package com.lucianbc.onechat.networking;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.lang.reflect.Field;

class ObjectMapperProvider {

    private static ObjectMapper mapper;

    static ObjectMapper getInstance() {
        if (mapper == null) mapper = initMapper();
        return mapper;
    }

    private ObjectMapperProvider() {}

    private static ObjectMapper initMapper() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            Class sessionClass = Class.forName("com.lucianbc.onechat.server.Session");
            SimpleModule sessionModule = new SimpleModule();
            sessionModule.addSerializer(sessionClass, new JsonSerializer<Object>() {
                @Override
                public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                    try {
                        Field field = o.getClass().getDeclaredField("user");
                        field.setAccessible(true);
                        Object ob = field.get(o);
                        field.setAccessible(false);
                        jsonGenerator.writeObject(ob);
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        jsonGenerator.writeObject(new Object());
                    }
                }
            });
            mapper.registerModule(sessionModule);
        } catch (ClassNotFoundException ex) {
            // simply don't do anything
        }

        return mapper;
    }
}
