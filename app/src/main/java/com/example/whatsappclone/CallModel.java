package com.example.whatsappclone;

/**
 * CallModel — Calls tab ma ek call item represent kare che
 * Fields:
 *   name      — caller/receiver name
 *   time      — call date/time
 *   callType  — "Incoming", "Outgoing", "Missed"
 */
public class CallModel {
    public String name;
    public String time;
    public String callType; // "Incoming" / "Outgoing" / "Missed"

    public CallModel() {}

    public CallModel(String name, String time, String callType) {
        this.name = name;
        this.time = time;
        this.callType = callType;
    }
}
