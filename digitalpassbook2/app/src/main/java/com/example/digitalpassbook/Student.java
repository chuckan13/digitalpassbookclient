package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("id")
    int id;

    @SerializedName("fullName")
    String fullName;

    @SerializedName("netID")
    String netId;

    public Student(int id, String fullName, String netID) {
        this.id = id;
        this.fullName = fullName;
        this.netId = netID;
    }
    public Student(String fullName, String netID) {
        this.fullName = fullName;
        this.netId = netID;
    }
}
