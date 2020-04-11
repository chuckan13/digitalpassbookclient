package com.example.digitalpassbook2.server

import com.google.gson.annotations.SerializedName

class Organization(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("logo") val logo: String
) {
    @SerializedName("id")
    val id = 0

}