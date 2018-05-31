package com.lucianbc.onechat.server;

import java.io.FileInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class OneChatServer {

    public static void main(String[] args) throws Exception {
        initPermissionTimeout();
        try (ServerSocket server = new ServerSocket(5000)) {
            SessionManager sessionManager = new SessionManager();
            ChatRoomManager chatRoomManager = new ChatRoomManager(sessionManager);

            while (true) {
                System.out.println("Waiting for a connection");
                Socket client = server.accept();
                System.out.println("Connection made");
                System.out.println(client);


                ClientHandler handler = new ClientHandler(client, sessionManager, chatRoomManager);
                Thread thread = new Thread(handler);
                thread.start();
            }
        }
    }

    private static void initPermissionTimeout() {
        System.out.println("Try to read file");
        try (FileInputStream fis = new FileInputStream("server.properties"); Scanner scanner = new Scanner(fis)) {
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split("=");
                WritePermissionManager.ACCESS_TIME = Integer.parseInt(data[1]);
            }
        } catch (Exception ex) {
            System.out.println("Exception");
            WritePermissionManager.ACCESS_TIME = 3600;
        }
    }
}

