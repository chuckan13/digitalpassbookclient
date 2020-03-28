package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Pass {

    @SerializedName("id")
    int id;

    @SerializedName("orgId")
    int orgID;

    @SerializedName("userId")
    int userID;

    @SerializedName("eventId")
    int eventID;

    @SerializedName("passName")
    String passName;

    public Pass (int passID, int orgID, int userID, int eventID, String passName) {
        this.id = passID;
        this.orgID = orgID;
        this.userID = userID;
        this.eventID = eventID;
        this.passName = passName;
    }
    public Pass (int orgID, int userID, int eventID, String passName) {
        this.orgID = orgID;
        this.userID = userID;
        this.eventID = eventID;
        this.passName = passName;
    }
}
