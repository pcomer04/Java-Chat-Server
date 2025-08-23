package com.peyton.chat_server.server;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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

    public void broadcast(String message, ClientHandler sender){
        for (ClientHandler client : clientHandlerList){
            if(client != sender){
                client.sendMessage(message);
            }
        }
    }

    public void broadcastSystemMessage(String message){
        for (ClientHandler client : clientHandlerList){
            client.sendMessage(message);
        }
    }
}
