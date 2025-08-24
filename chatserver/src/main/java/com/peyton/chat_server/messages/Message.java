package com.peyton.chat_server.messages;


/* Abstract class for messaging. Allows different message types for the server.
 * Can be extended further beyond the client message and system message with private messaging
 * in the future.
 */
public abstract class Message {
    protected String sender;
    protected String message;

    public Message(String sender, String message){
        this.sender = sender;
        this.message = message;
    }

    public String getSender() { 
        return sender; 
    }

    public String getContent() { 
        return message; 
    }

    public abstract String process();

}
