package com.peyton.chat_server;

import com.peyton.chat_server.server.ChatServer;

public class Main {
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.startServer(1234);
    }
}