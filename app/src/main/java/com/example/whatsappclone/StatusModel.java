package com.example.whatsappclone;

/**
 * StatusModel — Status tab ma ek status item represent kare che
 * (College demo mate dummy/static data use karva ma avse)
 */
public class StatusModel {
    public String name;      // User nu naam
    public String time;      // "10 minutes ago", "2 hours ago" etc.
    public boolean isMyStatus; // Pehelu item "My Status" che

    public StatusModel() {}

    public StatusModel(String name, String time, boolean isMyStatus) {
        this.name = name;
        this.time = time;
        this.isMyStatus = isMyStatus;
    }
}
