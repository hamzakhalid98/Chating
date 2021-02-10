package com.example.chat;

public class chat {
    String name;
    String chat;

    public chat() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    @Override
    public String toString() {
        return "chat{" +
                "name='" + name + '\'' +
                ", chat='" + chat + '\'' +
                '}';
    }
}
