package com.example.digitalpassbook2.server

import com.google.gson.annotations.SerializedName
import java.util.*

class Pass(
    @field:SerializedName("orgId") val orgId: Int,
    @field:SerializedName("userId") var userId: Int,
    @field:SerializedName("eventsId") val eventId: Int,
    @field:SerializedName("date") var date: Date
) {
    @SerializedName("id")
    val id = 0
}