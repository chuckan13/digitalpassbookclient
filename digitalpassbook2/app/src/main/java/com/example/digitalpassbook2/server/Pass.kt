package com.example.digitalpassbook2.server

import com.google.gson.annotations.SerializedName

class Pass(
    @field:SerializedName("orgId") val orgId: Int,
    @field:SerializedName("userId") var userId: Int,
    @field:SerializedName("eventsId") val eventId: Int,
    @field:SerializedName("date") var date: String,
    @field:SerializedName("owners") var owners: Array<String>
) {
    @SerializedName("id")
    val id = 0
}