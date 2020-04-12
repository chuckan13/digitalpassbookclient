package com.example.digitalpassbook2.server

import com.google.gson.annotations.SerializedName

class Organization(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("logo") val logo: String,
    @field:SerializedName("signin") val signin: String,
    @field:SerializedName("password") val password: String
) {
    @SerializedName("id")
    val id = 0
}