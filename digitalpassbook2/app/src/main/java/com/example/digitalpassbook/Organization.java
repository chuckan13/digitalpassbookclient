package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Organization {

    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name;

    public Organization(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Organization(String name) {
        this.name = name;
    }
}