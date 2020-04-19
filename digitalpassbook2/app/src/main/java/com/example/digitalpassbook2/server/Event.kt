package com.example.digitalpassbook2.server

import com.google.gson.annotations.SerializedName
import java.util.*

class Event {
    @SerializedName("id")
    val id = 0

    @SerializedName("orgId")
    var orgId: Int
        private set

    @SerializedName("date")
    var startDate: String
        private set

    @SerializedName("endDate")
    var endDate: String
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
    var cutoff: String
        private set

    @SerializedName("allStudentsVisibility")
    var allStudentsVisibility: Boolean
        private set

    constructor(orgID: Int, startDate: String, endDate: String, eventName: String, description: String,
                location: String, transferability: Boolean, openTimeVisibility: Boolean,
                closeDateVisibility: Boolean, closeTimeVisibility: Boolean, cutoff : String,
                allStudentsVisibility: Boolean) {
        this.orgId = orgID
        this.startDate = startDate
        this.endDate = endDate
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

    constructor(orgID: Int) {
        this.orgId = orgID
        this.startDate = Date().toString()
        this.endDate = Date().toString()
        this.name = ""
        this.description = ""
        this.location = ""
        this.transferability = false
        this.openTimeVisibility = false
        this.closeDateVisibility = false
        this.closeTimeVisibility = false
        this.cutoff = Date().toString()
        this.allStudentsVisibility = false
    }

}