package com.peyton.chat_server.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private Socket socket;
    private BufferedReader input;       // from server
    private PrintWriter output;         // to server
    private BufferedReader consoleInput; // from user keyboard

    public ChatClient(String serverAddress, int serverPort) {
        try {
            // 1. Connect to the server
            socket = new Socket(serverAddress, serverPort);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            consoleInput = new BufferedReader(new InputStreamReader(System.in));

            // 2. Handle server messages in a new thread
            new Thread(new ServerListener()).start();

            // 3. Send user input to server
            String userInput;
            while ((userInput = consoleInput.readLine()) != null) {
                output.println(userInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Thread for continuously reading messages from the server
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

    // Run the client
    public static void main(String[] args) {
        // Example: connect to localhost on port 1234
        new ChatClient("127.0.0.1", 1234);
    }
}
