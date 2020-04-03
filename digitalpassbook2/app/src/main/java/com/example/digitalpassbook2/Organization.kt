package com.example.digitalpassbook2

import com.google.gson.annotations.SerializedName

class Organization(@field:SerializedName("name") val name: String) {
    @SerializedName("id")
    val id = 0

}