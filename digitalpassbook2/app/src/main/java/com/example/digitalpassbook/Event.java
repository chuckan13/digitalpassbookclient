package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("id")
    int id;

    @SerializedName("orgId")
    int orgId;

    @SerializedName("eventName")
    String eventName;

    @SerializedName("description")
    String description;

    @SerializedName("date")
    String date;

    @SerializedName("startTime")
    String startTime;

    @SerializedName("endTime")
    String endTime;

    @SerializedName("location")
    String location;

    public Event (int eventID, int orgID, String eventName, String description, String date,
                  String starttime, String endtime, String location) {
        this.id = eventID;
        this.orgId = orgID;
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.startTime = starttime;
        this.endTime = endtime;
        this.location = location;
    }
    public Event (int orgID, String eventName, String description, String date,
                  String starttime, String endtime, String location) {
        this.orgId = orgID;
        this.eventName = eventName;
        this.description = description;
        this.date = date;
        this.startTime = starttime;
        this.endTime = endtime;
        this.location = location;
    }
    public Event (int orgID, String eventName) {
        this.orgId = orgID;
        this.eventName = eventName;
        this.description = " ";
        this.date = " ";
        this.startTime = " ";
        this.endTime = " ";
        this.location = " ";
    }
}
