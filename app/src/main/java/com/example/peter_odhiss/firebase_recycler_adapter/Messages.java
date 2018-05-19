package com.example.peter_odhiss.firebase_recycler_adapter;

public class Messages {
    private String message;
    private String sender;

    public Messages() {
    }

    public Messages(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
