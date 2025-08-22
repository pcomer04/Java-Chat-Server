package com.peyton.chat_server.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    Socket clientSocket;
    BufferedReader input;
    PrintWriter output;
    String username;
    ChatRoom chatRoom;

    public ClientHandler(Socket clientSocketInput, ChatRoom serverChatRoomInput){
        clientSocket = clientSocketInput;
        chatRoom = serverChatRoomInput;
    }

    @Override
    public void run(){
        try{
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            
            while(!clientSocket.isClosed()){
                String message = input.readLine();
                if (message != null){
                    chatRoom.broadcast(message, this);
                } else{
                    break;
                }
            }
        
        } catch(IOException e){
            e.printStackTrace();
        }
        finally{
            chatRoom.removeClient(this);
            try{
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
