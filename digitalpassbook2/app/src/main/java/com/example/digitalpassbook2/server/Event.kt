package com.example.digitalpassbook2.server

import com.google.gson.annotations.SerializedName

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

    @SerializedName("openTimeVisibility")
    var openTimeVisibility: Boolean
        private set

    @SerializedName("closeDateVisibility")
    var closeDateVisibility: Boolean
        private set

    @SerializedName("closeTimeVisibility")
    var closeTimeVisibility: Boolean
        private set

    @SerializedName("allStudentsVisibility")
    var allStudentsVisibility: Boolean
        private set

    constructor(
        orgID: Int, eventName: String, description: String, date: String,
        starttime: String, endtime: String, location: String, openTimeVisibility: Boolean,
        closeDateVisibility: Boolean, closeTimeVisibility: Boolean, allStudentsVisibility: Boolean
    ) {
        this.orgId = orgID
        this.name = eventName
        this.description = description
        this.date = date
        this.startTime = starttime
        this.endTime = endtime
        this.location = location
        this.openTimeVisibility = openTimeVisibility
        this.closeDateVisibility = closeDateVisibility
        this.closeTimeVisibility = closeTimeVisibility
        this.allStudentsVisibility = allStudentsVisibility
    }

    constructor(orgID: Int, eventName: String) {
        this.orgId = orgID
        this.name = eventName
        this.description = " "
        this.date = " "
        this.startTime = " "
        this.endTime = " "
        this.location = " "
        this.openTimeVisibility = false
        this.closeDateVisibility = false
        this.closeTimeVisibility = false
        this.allStudentsVisibility = false
    }

}