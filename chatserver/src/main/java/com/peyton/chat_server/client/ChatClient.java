package com.peyton.chat_server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/* 
 * This is a simple client for the chat server. This class enables real-time, two-way communication between a user and a chat server.
 * 
 * Responsibilities:
 * - Connects to the server using a TCP socket.
 * - Reads user input from the console and sends it to the server.
 * - Listens for messages from the server and prints them to the console with a separate thread.
 * 
 * Relationship to OOP:
 * Encapsulates all of the data and logic needed for a chat client. Provides a high-level abstraction of a chat client, the user 
 * does not need to know anything about sockets or threading. Composition in the fact that the class is composed of other objects like
 * Socket, BufferedReader and PrintWriter. The ServerListener implemenets Runnable allowing it to be used polymorphically.
 */

public class ChatClient {
    private Socket socket;
    private BufferedReader input;       
    private PrintWriter output;         
    private BufferedReader consoleInput; 

    public ChatClient(String serverAddress, int serverPort) {
        try {
            socket = new Socket(serverAddress, serverPort);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // Separate thread for lisenting to messages from the server
            new Thread(new ServerListener()).start();

            // Reads the input from the console, if not null it outputs
            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                output.println(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    /* 
     * This is a private inner class to listen for messages from the server on a separate thread.
     * This is to allow the client to recieve messages while still being able to send them. Safely
     * closes when the server disconnects, reading null in the input.
     */
    private class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                String serverMessage;
                while ((serverMessage = input.readLine()) != null) {
                    System.out.println(serverMessage);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ChatClient("127.0.0.1", 1234);
    }
}
