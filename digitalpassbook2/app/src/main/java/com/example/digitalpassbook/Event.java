package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("id")
    private int id;

    @SerializedName("orgId")
    private int orgId;

    @SerializedName("eventName")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("date")
    private String date;

    @SerializedName("startTime")
    private String start;

    @SerializedName("endTime")
    private String ends;

    @SerializedName("location")
    private String location;

    public Event (int orgID, String eventName, String description, String date,
                  String starttime, String endtime, String location) {
        this.orgId = orgID;
        this.name = eventName;
        this.description = description;
        this.date = date;
        this.start = starttime;
        this.ends = endtime;
        this.location = location;
    }
    public Event (int orgID, String eventName) {
        this.orgId = orgID;
        this.name = eventName;
        this.description = " ";
        this.date = " ";
        this.start = " ";
        this.ends = " ";
        this.location = " ";
    }

    public int getId() { return this.id; }
    public int getOrgId() { return this.orgId; }
    public String getName() { return this.name; }
    public String getDescription() { return this.description; }
    public String getDate() { return this.date; }
    public String getStartTime() { return this.start; }
    public String getEndTime() { return this.ends; }
    public String getLocation() { return this.location; }
}
