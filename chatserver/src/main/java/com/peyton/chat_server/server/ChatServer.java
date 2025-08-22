package com.peyton.chat_server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    ServerSocket chatServerSocket;
    ChatRoom serverChatRoom;

    public void startServer(int portNumber){
        try{
            chatServerSocket = new ServerSocket(portNumber);
            serverChatRoom = new ChatRoom();

            System.out.println("Chat server started on port " + portNumber);

            while(true){
                try {
                    Socket clientSocket = chatServerSocket.accept();

                    ClientHandler handler = new ClientHandler(clientSocket, serverChatRoom);
                    new Thread(handler).start();
                } catch (IOException e){
                    System.out.println("Accept failed on:" + portNumber);
                }
            }

        } catch(IOException e){
            System.err.println("Could not listen on port number:" + portNumber);
            System.exit(1);
        }
        
    }


}
