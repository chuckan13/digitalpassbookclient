package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Student {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("netId")
    private String netId;

    public Student(String fullName, String netID) {
        this.name = fullName;
        this.netId = netID;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getNetid() { return this.netId; }
}
