package com.example.whatsappclone;

/**
 * ChatModel — ek chat row represent kare che (chat list ma)
 * Fields: name (user name), message (last message), time, unreadCount, userId (Firebase UID)
 */
public class ChatModel {

    public String name;
    public String message;
    public String time;
    public int unreadCount;
    public String userId;  // Firebase UID — chat open karvane mate

    // Full constructor
    public ChatModel(String name, String message, String time, int unreadCount, String userId) {
        this.name = name;
        this.message = message;
        this.time = time;
        this.unreadCount = unreadCount;
        this.userId = userId;
    }

    // Default no-arg constructor (Firebase requires this)
    public ChatModel() {}
}
