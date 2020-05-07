package com.example.digitalpassbook2.server

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Pass(
    @field:SerializedName("orgId") val orgId: Int,
    @field:SerializedName("userId") var userId: Int,
    @field:SerializedName("eventsId") val eventId: Int,
    @field:SerializedName("date") var date: String,
    @field:SerializedName("owners") var owners: Array<String>,
    @field:SerializedName("isLocked") var isLocked: Boolean
): Parcelable {
    @SerializedName("id")
    val id = 0
}