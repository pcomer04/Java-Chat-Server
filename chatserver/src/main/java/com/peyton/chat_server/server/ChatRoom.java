package com.peyton.chat_server.server;

import java.util.List;

public class ChatRoom {
    List<ClientHandler> clientHandlerList;

    public void addClient(ClientHandler clientHandler){
        if (clientHandler != null){
            clientHandlerList.add(clientHandler);
        }
    }
    
    public void removeClient(ClientHandler clientHandler){
        if (clientHandler != null) {
            clientHandlerList.remove(clientHandler);
        }
    }

    public void broadcast(String message, ClientHandler sender){

    }
}
