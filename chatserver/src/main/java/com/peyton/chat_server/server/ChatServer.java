package com.peyton.chat_server.server;

import java.net.ServerSocket;

public class ChatServer {
    ServerSocket chatServerSocket;
    ChatRoom serverChatRoom;

    public void startServer(int portNumber){
        try{
            chatServerSocket = new ServerSocket(portNumber);

        }
        


    }
}
