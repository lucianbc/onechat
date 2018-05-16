package com.lucianbc.onechat.networking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import static org.junit.Assert.*;

public class RequestMapperTest {

    @Test
    public void mapperTest() throws Exception {
        DummyClass ref = new DummyClass(33, "test");
        RequestMapper mapper = new RequestMapper();
        mapper.register("/test", e -> {
            assertEquals(e.getClass(), DummyClass.class);
            assertEquals(ref, e);
        }, DummyClass.class);
        String data = ObjectMapperProvider.getInstance().writeValueAsString(ref);
        mapper.fire("/test", data);
    }
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class DummyClass {
    private int val1;
    private String str;
}