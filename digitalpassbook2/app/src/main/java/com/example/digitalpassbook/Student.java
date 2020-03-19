package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("studentid")
    int studentID;

    @SerializedName("fullname")
    String fullName;

    @SerializedName("netid")
    String netID;

    public Student(int studentID, String fullName, String netID) {
        this.studentID = studentID;
        this.fullName = fullName;
        this.netID = netID;
    }
    public Student(String fullName, String netID) {
        this.fullName = fullName;
        this.netID = netID;
    }
}
