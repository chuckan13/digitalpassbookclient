package com.example.digitalpassbook;
import com.google.gson.annotations.SerializedName;

public class Organization {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public Organization(String name) {
        this.name = name;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }

}