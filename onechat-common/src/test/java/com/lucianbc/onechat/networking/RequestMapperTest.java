package com.lucianbc.onechat.networking;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void genericMapperTest() throws Exception {
        DummyClass ref = new DummyClass(33, "test");
        DummyGenericClass<String, DummyClass> parent = new DummyGenericClass<>("valoare", ref);
        RequestMapper mapper = new RequestMapper();

        List<Class> l = new LinkedList<>();
        l.add(DummyGenericClass.class);
        l.add(String.class);
        l.add(DummyClass.class);

        mapper.register("/genTest", e -> {
            assertEquals(e.getClass(), DummyGenericClass.class);
            assertEquals(e, parent);
        }, l);
        String data = ObjectMapperProvider.getInstance().writeValueAsString(parent);
        mapper.fire("/genTest", data);
    }


}

@Data
@AllArgsConstructor
@NoArgsConstructor
class DummyClass {
    private int val1;
    private String str;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class DummyGenericClass<V, T> {
    private V val;
    private T typed;
}
