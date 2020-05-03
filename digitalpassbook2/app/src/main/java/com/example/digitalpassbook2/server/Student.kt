package com.example.digitalpassbook2.server

import com.google.gson.annotations.SerializedName

class Student(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("netID") val netid: String,
    @field:SerializedName("password") val password: String,
    @field:SerializedName("orgId") val orgId: Int,
    @field:SerializedName("email") val email: String,
    @field:SerializedName("princetonStudent") var princetonStudent: Boolean,
    @field:SerializedName("defaultPasses") var defaultPasses: Int,
    @field:SerializedName("barCodeNumber") var barCodeNumber: Int,
    @field:SerializedName("graduatingClass") var graduatingClass: Int
) {
    @SerializedName("id")
    val id = 0

}