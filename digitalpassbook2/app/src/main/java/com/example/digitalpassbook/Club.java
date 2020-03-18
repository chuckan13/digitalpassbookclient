package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Club {
    @SerializedName("clubid")
    int clubID;

    @SerializedName("clubname")
    String clubName;

    public Club (int clubID, String clubName) {
        this.clubID = clubID;
        this.clubName = clubName;
    }
    public Club (String clubName) {
        this.clubName = clubName;
    }
}
