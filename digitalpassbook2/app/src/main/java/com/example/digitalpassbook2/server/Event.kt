package com.example.digitalpassbook2.server

import com.google.gson.annotations.SerializedName
import java.util.*

class Event {
    @SerializedName("id")
    val id = 0

    @SerializedName("orgId")
    var orgId: Int
        private set

    @SerializedName("eventName")
    var name: String
        private set

    @SerializedName("description")
    var description: String
        private set

    @SerializedName("date")
    var date: String
        private set

    @SerializedName("startTime")
    var startTime: String
        private set

    @SerializedName("endTime")
    var endTime: String
        private set

    @SerializedName("location")
    var location: String
        private set

    constructor(
        orgID: Int, eventName: String, description: String, date: String,
        starttime: String, endtime: String, location: String
    ) {
        orgId = orgID
        name = eventName
        this.description = description
        this.date = date
        startTime = starttime
        endTime = endtime
        this.location = location
    }

    constructor(orgID: Int, eventName: String) {
        orgId = orgID
        name = eventName
        description = " "
        date = " "
        startTime = " "
        endTime = " "
        location = " "
    }

}