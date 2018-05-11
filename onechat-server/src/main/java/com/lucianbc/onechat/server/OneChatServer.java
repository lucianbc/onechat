package com.lucianbc.onechat.server;

import java.net.ServerSocket;
import java.net.Socket;

public class OneChatServer {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(5000);
        while (true) {
            System.out.println("Waiting for a connection");
            Socket client = server.accept();
            System.out.println("Connection made");
            System.out.println(client);
            ClientHandler handler = new ClientHandler(client);
            Thread thread = new Thread(handler);
            thread.start();
        }
    }
}

