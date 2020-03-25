package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("id")
    int id;

    @SerializedName("orgid")
    int orgid;

    @SerializedName("eventname")
    String eventname;

    @SerializedName("description")
    String description;

    @SerializedName("date")
    String date;

    @SerializedName("starttime")
    String starttime;

    @SerializedName("endtime")
    String endtime;

    @SerializedName("location")
    String location;

    public Event (int eventID, int orgID, String eventName, String description, String date,
                  String starttime, String endtime, String location) {
        this.id = eventID;
        this.orgid = orgID;
        this.eventname = eventName;
        this.description = description;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.location = location;
    }
    public Event (int orgID, String eventName, String description, String date,
                  String starttime, String endtime, String location) {
        this.orgid = orgID;
        this.eventname = eventName;
        this.description = description;
        this.date = date;
        this.starttime = starttime;
        this.endtime = endtime;
        this.location = location;
    }
    public Event (int orgID, String eventName) {
        this.orgid = orgID;
        this.eventname = eventName;
        this.description = "";
        this.date = "";
        this.starttime = "";
        this.endtime = "";
        this.location = "";
    }
}
