package com.peyton.chat_server.messages;

public class ClientMessage extends Message {
    
    public ClientMessage(String sender, String message){
        super(sender, message);
    }

    @Override
    public String process(){
        return sender + ": " + message;
    }
}
