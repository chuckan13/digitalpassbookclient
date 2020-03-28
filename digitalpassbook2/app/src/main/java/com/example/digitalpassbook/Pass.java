package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Pass {

    @SerializedName("id")
    int id;

    @SerializedName("organizationid")
    int organizationid;

    @SerializedName("userid")
    int userid;

    @SerializedName("eventsid")
    int eventsid;

    @SerializedName("passName")
    String passName;

    public Pass (int passID, int orgID, int userID, int eventID, String passName) {
        this.id = passID;
        this.organizationid = orgID;
        this.userid = userID;
        this.eventsid = eventID;
        this.passName = passName;
    }
    public Pass (int orgID, int userID, int eventID, String passName) {
        this.organizationid = orgID;
        this.userid = userID;
        this.eventsid = eventID;
        this.passName = passName;
    }
}
