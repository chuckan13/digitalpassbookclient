package com.example.digitalpassbook2.server

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.annotations.SerializedName
import java.sql.Time
import java.time.LocalDateTime
import java.util.*

class Event {
    @SerializedName("id")
    val id = 0

    @SerializedName("orgId")
    var orgId: Int
        private set

    @SerializedName("date")
    var startDate: Date
        private set

    @SerializedName("startTime")
    var startTime: Time
        private set

    @SerializedName("endDate")
    var endDate: Date
        private set

    @SerializedName("endTime")
    var endTime: Time
        private set

    @SerializedName("eventName")
    var name: String
        private set

    @SerializedName("description")
    var description: String
        private set

    @SerializedName("location")
    var location: String
        private set

    @SerializedName("transferability")
    var transferability: Boolean
        private set

    @SerializedName("openTimeVisibility")
    var openTimeVisibility: Boolean
        private set

    @SerializedName("closeDateVisibility")
    var closeDateVisibility: Boolean
        private set

    @SerializedName("closeTimeVisibility")
    var closeTimeVisibility: Boolean
        private set

    @SerializedName("cutoff")
    var cutoff: LocalDateTime
        private set

    @SerializedName("allStudentsVisibility")
    var allStudentsVisibility: Boolean
        private set

    constructor(
        orgID: Int, startDate: Date, startTime: Time, endDate: Date, endTime: Time,
        eventName: String, description: String, location: String, transferability: Boolean,
        openTimeVisibility: Boolean, closeDateVisibility: Boolean, closeTimeVisibility: Boolean,
        cutoff : LocalDateTime, allStudentsVisibility: Boolean
    ) {
        this.orgId = orgID
        this.startDate = startDate
        this.startTime = startTime
        this.endDate = endDate
        this.endTime = endTime
        this.name = eventName
        this.description = description
        this.location = location
        this.transferability = transferability
        this.openTimeVisibility = openTimeVisibility
        this.closeDateVisibility = closeDateVisibility
        this.closeTimeVisibility = closeTimeVisibility
        this.cutoff = cutoff
        this.allStudentsVisibility = allStudentsVisibility
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(orgID: Int) {
        this.orgId = orgID
        this.startDate = Date()
        this.startTime = Time(System.currentTimeMillis())
        this.endDate = Date()
        this.endTime = Time(System.currentTimeMillis())
        this.name = ""
        this.description = ""
        this.location = ""
        this.transferability = false
        this.openTimeVisibility = false
        this.closeDateVisibility = false
        this.closeTimeVisibility = false
        this.cutoff = LocalDateTime.of(this.endDate.year, this.endDate.month, this.endDate.day,
            this.endTime.hours, this.endTime.minutes, this.endTime.seconds)
        this.allStudentsVisibility = false
    }

}