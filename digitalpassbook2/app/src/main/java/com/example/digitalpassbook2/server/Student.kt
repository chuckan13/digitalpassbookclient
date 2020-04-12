package com.example.digitalpassbook2.server

import com.google.gson.annotations.SerializedName

class Student(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("netID") val netid: String,
    @field:SerializedName("password") val password: String,
    @field:SerializedName("orgId") val orgId: Int
) {
    @SerializedName("id")
    val id = 0

}