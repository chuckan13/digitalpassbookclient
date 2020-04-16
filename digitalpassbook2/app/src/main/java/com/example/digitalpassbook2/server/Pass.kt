package com.example.digitalpassbook2.server

import com.google.gson.annotations.SerializedName

class Pass(
    @field:SerializedName("orgId") val orgId: Int,
    @field:SerializedName("userId") var userId: Int,
    @field:SerializedName("eventsId") val eventId: Int,
    @field:SerializedName("passName") var passName: String,
    @field:SerializedName("transferability") var transferability: Boolean,
    @field:SerializedName("cutoff") var cutoff: String?,
    @field:SerializedName("date") var date: String?
) {
    @SerializedName("id")
    val id = 0
}