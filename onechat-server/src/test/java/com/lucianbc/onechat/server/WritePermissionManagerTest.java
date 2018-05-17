package com.lucianbc.onechat.server;

import java.util.LinkedList;
import java.util.List;

public class WritePermissionManagerTest {

    private List<UserSessionId> myUsis;

    public void testPermissionManager() throws Exception {
        WritePermissionManager permissionManager = new WritePermissionManager(customContract(), null);
        permissionManager.handleRequest(new UserSessionId("usi2"));
        permissionManager.handleRequest(new UserSessionId("asd"));
        permissionManager.handleRequest(new UserSessionId("usi1"));
        permissionManager.handleRequest(new UserSessionId("usi1"));
    }

    private SessionWriteContract customContract() {
        myUsis = new LinkedList<>();
        myUsis.add(new UserSessionId("usi1"));
        myUsis.add(new UserSessionId("usi2"));
        return new SessionWriteContract() {
            @Override
            public boolean isAvailable(UserSessionId id) {
                return myUsis.contains(id);
            }

            @Override
            public void sendWriteAccess(UserSessionId id, boolean access, String roomId) {
                System.out.println(String.format("Giving %b to %s", access, id));
            }
        };
    }

    public static void main(String[] args) throws Exception {
        WritePermissionManagerTest t = new WritePermissionManagerTest();
        t.testPermissionManager();
//        ObjectMapper mapper = new ObjectMapper();
//        String v = mapper.writeValueAsString(true);
//        System.out.println(v);
//        Boolean vv = mapper.readValue(v, Boolean.class);
//        System.out.println(vv);
    }
}