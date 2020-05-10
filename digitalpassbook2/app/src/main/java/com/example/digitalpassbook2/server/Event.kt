package com.example.digitalpassbook2.server

import com.google.gson.annotations.SerializedName
import java.util.*

class Event {
    @SerializedName("id")
    val id = 0

    @SerializedName("orgId")
    var orgId: Int

    @SerializedName("date")
    var startDate: String

    @SerializedName("endDate")
    var endDate: String

    @SerializedName("eventName")
    var name: String

    @SerializedName("description")
    var description: String

    @SerializedName("location")
    var location: String

    @SerializedName("transferability")
    var transferability: Boolean

    @SerializedName("openTimeVisibility")
    var openTimeVisibility: Boolean

    @SerializedName("closeDateVisibility")
    var closeDateVisibility: Boolean

    @SerializedName("closeTimeVisibility")
    var closeTimeVisibility: Boolean

    @SerializedName("cutoff")
    var cutoff: String

    @SerializedName("allStudentsVisibility")
    var allStudentsVisibility: Boolean

    @SerializedName("bouncers")
    var bouncers: Array<String>

    @SerializedName("numArrived")
    var numArrived: Int

    constructor(orgID: Int, startDate: String, endDate: String, eventName: String, description: String,
                location: String, transferability: Boolean, openTimeVisibility: Boolean,
                closeDateVisibility: Boolean, closeTimeVisibility: Boolean, cutoff : String,
                allStudentsVisibility: Boolean, bouncers: Array<String>, numArrived: Int) {
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
        this.bouncers = bouncers
        this.numArrived = numArrived
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
        this.bouncers = arrayOf()
        this.numArrived = 0
    }

}