package com.lucianbc.onechat.networking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class NetworkEndpoint {
    private static Logger logger = LoggerFactory.getLogger(NetworkEndpoint.class);

    private static final int TIMEOUT = 10000;

    private PrintWriter writer;
    private BufferedReader reader;
    private Socket socket;

    private Receiver receiver;
    private Sender sender;

    private Command onConnectionClosed;

    public NetworkEndpoint(String host, int port, RequestMapper mapper) throws IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), TIMEOUT);

        init(socket, mapper);
    }

    public NetworkEndpoint(Socket socket, RequestMapper mapper) throws IOException {
        init(socket, mapper);
    }

    private void listen() {
        while (true) {
            try {
                String line = reader.readLine();
                if (line == null) {
                    logger.info("Received null. Connection stopped by remote site.");
                    if (onConnectionClosed != null) onConnectionClosed.invoke();
                    break;
                }
                receiver.handleRequest(line);
            } catch (IOException e) {
                logger.info("Connection closed by the client.");
                if (onConnectionClosed != null) onConnectionClosed.invoke();
                break;
            }
        }
    }

    public void startListen() {
        Thread thread = new Thread(this::listen);
        thread.start();
    }

    public void send(String path, Object data) {
        try {
            String request = sender.toRequest(path, data);
            writer.println(request);
        } catch (BadPayloadException e) {
            logger.error("Error sending request", e);
        }
    }

    private void init(Socket socket, RequestMapper mapper) throws IOException {
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socket = socket;
        this.sender = new Sender();
        this.receiver = new Receiver(mapper);
    }

    public void stop() throws IOException {
        if (writer != null) writer.close();
        if (reader != null) reader.close();
        if (socket != null) socket.close();
    }

    public void onConnectionClosed(Command command) {
        this.onConnectionClosed = command;
    }
}
