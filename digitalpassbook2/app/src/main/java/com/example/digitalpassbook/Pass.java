package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Pass {

    @SerializedName("passid")
    int passID;

    @SerializedName("clubid")
    int clubID;

    @SerializedName("userid")
    int userID;

    @SerializedName("eventid")
    int eventID;

    @SerializedName("passname")
    String passName;

    public Pass (int passID, int clubID, int userID, int eventID, String passName) {
        this.passID = passID;
        this.clubID = clubID;
        this.userID = userID;
        this.eventID = eventID;
        this.passName = passName;
    }
    public Pass (int clubID, int userID, int eventID, String passName) {
        this.clubID = clubID;
        this.userID = userID;
        this.eventID = eventID;
        this.passName = passName;
    }
}
