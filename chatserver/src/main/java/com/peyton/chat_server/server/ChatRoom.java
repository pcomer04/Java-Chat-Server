package com.peyton.chat_server.server;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.peyton.chat_server.messages.Message;

/*
 * This class is the shared memory space for all of the clients that join the Chat Server. 
 * This class handles all of the ClientHandler threads and allows messages to be broadcasted between them.
 * The use of the CopyOnWriteArrayList is for thread safe modificiation from multiple threads. This class 
 * does not manage threads/processes directly but allows multiple threads to call functions without data
 * corruption.
 * 
 * Responsibilities:
 * - Add/Remove Clients - allows clients on separate threads to be added to a shared array for communication
 * - Broadcasting messages - allows the messaging of clients
 * 
 * Relationship to OOP:
 * This class encapsulates a list of ClientHandler objects but allows modification of this list through addClient, removeClient
 * and broadcast. This is an abstraction of how clients are manages internally. This class is composed of ClientHandler objects, 
 * displaying a "has-a" relationship.
 */
public class ChatRoom {
    private final List<ClientHandler> clientHandlerList = new CopyOnWriteArrayList<>();;

    public void addClient(ClientHandler clientHandler){
        if (clientHandler != null){
            clientHandlerList.add(clientHandler);
             System.out.println("Client joined. Total: " + clientHandlerList.size());
        }
    }
    
    public void removeClient(ClientHandler clientHandler){
        if (clientHandler != null) {
            clientHandlerList.remove(clientHandler);
            System.out.println("Client left. Total: " + clientHandlerList.size());
        }
    }

    public void broadcast(Message message, ClientHandler sender){
        for (ClientHandler client : clientHandlerList){
            if(client != sender){
                client.sendMessage(message);
            }
        }
    }
}
