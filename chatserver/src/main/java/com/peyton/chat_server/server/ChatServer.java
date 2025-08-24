package com.peyton.chat_server.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/* The ChatServer exemplifies our process in this program. Every time a new client connects, it is started
 * in a new thread. This allows concurrent communication between the clients within our process.
 * 
 * Responsibilities:
 * - Start the Server - takes a port number and creates a server with a socket as well as an accompanying ChatRoom
 * - Constantly Listen for Client Connections - listens and creates threads for new connections
 * 
 * Relationship to OOP:
 * The server logic is encapsulated into this class and it manages all the things the server needs to while
 * using ChatRoom and ClientHandler for their respective needs. Abstracts the details behind socket management 
 * with the startServer() method. 
 */

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
