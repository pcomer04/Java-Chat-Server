package com.peyton.chat_server.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    private String username;
    private ChatRoom chatRoom;

    public ClientHandler(Socket clientSocketInput, ChatRoom serverChatRoomInput){
        clientSocket = clientSocketInput;
        chatRoom = serverChatRoomInput;
    }

    @Override
    public void run() {
        try {
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);

             output.println("Enter your username:");

            username = input.readLine();

            if (username == null || username.trim().isEmpty()) {
                username = "Guest" + clientSocket.getPort(); // or some random ID
            }

            chatRoom.addClient(this);
            chatRoom.broadcast(username + " has joined the chat!", this);

            String message;
            while ((message = input.readLine()) != null) {
                chatRoom.broadcast(message, this);
            }
        } catch (IOException e) {
            System.err.println("Connection error with client: " + e.getMessage());
        } finally {
            chatRoom.removeClient(this);
            chatRoom.broadcast(username + " has left the chat.", this);
            try { input.close(); } catch (IOException ignored) {}
            if (output != null) output.close();
            try { clientSocket.close(); } catch (IOException ignored) {}
        }
    }

    public String getUsername(){
        return username;
    }

    public void sendMessage(String message){
        output.println(username + ": " + message);
    }

}
