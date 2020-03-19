package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Club {
    @SerializedName("id")
    int id;

    @SerializedName("clubname")
    String clubname;

    public Club (int id, String clubname) {
        this.id = id;
        this.clubname = clubname;
    }
    public Club (String clubname) {
        this.clubname = clubname;
    }
}
