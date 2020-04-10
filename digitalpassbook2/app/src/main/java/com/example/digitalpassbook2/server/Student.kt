package com.example.digitalpassbook2.server

import com.google.gson.annotations.SerializedName

class Student(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("netID") val netid: String
) {
    @SerializedName("id")
    val id = 0

}