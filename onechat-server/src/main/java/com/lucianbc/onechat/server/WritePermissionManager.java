package com.lucianbc.onechat.server;

import lombok.RequiredArgsConstructor;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

@RequiredArgsConstructor
class WritePermissionManager {
    private static final int accessTime = 3600;

    private Queue<UserSessionId> requests = new ConcurrentLinkedDeque<>();
    private UserSessionId currentUser = null;
    private final SessionWriteContract writeContract;
    private final ChatRoom room;

    void handleRequest(UserSessionId requesterId) {
        requests.add(requesterId);
        reassignCurrentUser();
    }

    boolean canSend(UserSessionId usi) {
        if (currentUser == null) return true;
        return usi.equals(currentUser);
    }

    private synchronized void reassignCurrentUser() {
        if (currentUser != null) return;
        UserSessionId usi = requests.poll();
        if (usi == null) return;
        if (! writeContract.isAvailable(usi)) {
            reassignCurrentUser();
        } else {
            currentUser = usi;
            writeContract.sendWriteAccess(usi, true, room.getRoomId());
            Thread removeAccessThread = new Thread(() -> {
                try {
                    Thread.sleep(accessTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                writeContract.sendWriteAccess(currentUser, false, room.getRoomId());
                currentUser = null;
                reassignCurrentUser();
            });
            removeAccessThread.start();
        }
    }
}
