package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Club {
    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    public Club (int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Club (String name) {
        this.name = name;
    }
}
