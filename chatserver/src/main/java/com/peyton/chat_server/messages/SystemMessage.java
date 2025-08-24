package com.peyton.chat_server.messages;

public class SystemMessage extends Message {

    public SystemMessage(String message){
        super("SERVER", message);
    }

    @Override
    public String process(){
        return "[System] " + message;
    }
}
