package com.peyton.chat_server.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import com.peyton.chat_server.messages.ClientMessage;
import com.peyton.chat_server.messages.Message;
import com.peyton.chat_server.messages.SystemMessage;


/* 
 * This class is designed to be run on a thread. Each time a client connects to the server
 * a new ClientHandler instance is created and given a new Thread. This allows the server to handle multiple
 * clients concurrently, each client is managed by its own thread so no clients block or slow down others.
 * 
 * Responsibilities:
 * - Threaded Client Management - new thread for each connected client
 * - Input/Output Streams - sets up BufferedReader to read messages from client and PrintWriter to send messages.
 * - Username Handling - prompts for a username from a new user, if none is provided assigns a default.
 * - Joining/Leaving - adds clients to the chat room, announces their arrival and removes them accordingly.
 * - Message Broadcasting - listens for messages from the client and broadcasts them to all other clients.
 * - Resource Cleanup - when a user disconnects or an error occurs, closes the streams and the socket.
 * 
 * Relationship to OOP:
 * Encapsulates all the data and behavior to manage a single client connection, and methods like sendMessage and getUsername
 * provided controlled access to the classes functionality. For abstraction this class hides the complex details of handling
 * sockets, streams and client management behind simple method calls. Uses the Runnable interface, to exhibit polymorphic behavior\
 * with Run().
 * 
 */
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
            // I/O Stream Set Up
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            output.println("Enter your username:");

            username = input.readLine();

            // Username management
            if (username == null || username.trim().isEmpty()) {
                username = "Guest" + Math.random();
            }

            chatRoom.addClient(this);
            chatRoom.broadcast(new SystemMessage(username + " has joined the chat!"), this);

            // Message Broadcasting
            String message;
            while ((message = input.readLine()) != null) {
                Message chatMessage = new ClientMessage(username, message);
                chatRoom.broadcast(chatMessage, this);
            }
        } catch (IOException e) {
            System.err.println("Connection error with client: " + e.getMessage());
        } finally {
            // Resource Cleanup
            chatRoom.removeClient(this);
            chatRoom.broadcast(new SystemMessage(username + " has left the chat."), this);
            try { input.close(); } catch (IOException ignored) {}
            if (output != null) output.close();
            try { clientSocket.close(); } catch (IOException ignored) {}
        }
    }

    public String getUsername(){
        return username;
    }

    public void sendMessage(Message message){
        output.println(message.process());
    }

}
