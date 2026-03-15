package com.example.whatsappclone;

/**
 * MessageModel — ek single message represent kare che
 * Firebase ma /messages/{chatId}/{messageId}/ node ma save thay che
 *
 * Fields:
 *   senderId  — Firebase UID of the person who sent the message
 *   message   — actual message text
 *   timestamp — time in milliseconds (for sorting messages)
 */
public class MessageModel {

    public String senderId;
    public String message;
    public long timestamp;

    // Firebase requires a no-arg constructor
    public MessageModel() {}

    public MessageModel(String senderId, String message, long timestamp) {
        this.senderId = senderId;
        this.message = message;
        this.timestamp = timestamp;
    }
}
