package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Event {
    @SerializedName("eventid")
    int eventID;

    @SerializedName("clubid")
    int clubID;

    @SerializedName("eventname")
    String eventName;

    @SerializedName("time")
    String time;

    @SerializedName("location")
    String location;

    public Event (int eventID, int clubID, String eventName, String time, String location) {
        this.eventID = eventID;
        this.clubID = clubID;
        this.eventName = eventName;
        this.time = time;
        this.location = location;
    }
    public Event (int clubID, String eventName, String time, String location) {
        this.clubID = clubID;
        this.eventName = eventName;
        this.time = time;
        this.location = location;
    }
}
