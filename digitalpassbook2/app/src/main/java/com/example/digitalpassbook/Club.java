package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Club {
    @SerializedName("clubid")
    int clubid;

    @SerializedName("clubname")
    String clubname;

    public Club (int clubid, String clubname) {
        this.clubid = clubid;
        this.clubname = clubname;
    }
    public Club (String clubname) {
        this.clubname = clubname;
    }
    public Club(){}
}
