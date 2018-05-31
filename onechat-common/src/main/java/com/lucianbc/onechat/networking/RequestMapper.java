package com.lucianbc.onechat.networking;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RequestMapper {
    private static final Logger logger = LoggerFactory.getLogger(RequestMapper.class);

    private Map<String, MappingRecord> actions = new HashMap<>();
    private ObjectMapper mapper = ObjectMapperProvider.getInstance();


    @SuppressWarnings("unchecked")
    public void fire(String path, String serializedArg) throws BadPayloadException {
        MappingRecord record = actions.get(path);
        if (record == null) {
            logger.debug("No mapping found");
            logger.debug(String.format("%s %s", path, serializedArg));
            return;
        }
        try {
            Object arg = mapper.readValue(serializedArg, record.getParamType());
            record.getAction().accept(arg);
        } catch (IOException e) {
            throw new BadPayloadException(e.getMessage());
        }
    }

    public <T> void register(String path, Consumer<T> action, Class<T> argType) {
        TypeReference<T> typeReference = new TypeReference<T>() {
            @Override
            public Type getType() {
                return argType;
            }
        };
        MappingRecord record = new MappingRecord(action, typeReference);
        actions.put(path, record);
    }

    public <T> void register(String path, Consumer<T> action, List<Class> argTypes) {
        Class parent = argTypes.get(0);
        argTypes.remove(0);
        ParameterizedType parameterizedType = new ParameterizedType() {
            @Override
            public Type[] getActualTypeArguments() {
                Type[] types = new Type[argTypes.size()];
                for (int i = 0; i < types.length; i++) {
                    types[i] = argTypes.get(i);
                }
                return types;
            }

            @Override
            public Type getRawType() {
                return parent;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }
        };

        TypeReference<T> reference = new TypeReference<T>() {
            @Override
            public Type getType() {
                return parameterizedType;
            }
        };

        MappingRecord record = new MappingRecord(action, reference);
        actions.put(path, record);
    }
    @AllArgsConstructor
    private class MappingRecord {
        @Getter
        private Consumer action;
        @Getter private TypeReference paramType;
    }
}
