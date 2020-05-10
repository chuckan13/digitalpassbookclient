package com.example.digitalpassbook2.server

import com.google.gson.annotations.SerializedName

class Organization(
    @field:SerializedName("name") val name: String,
    @field:SerializedName("logo") val logo: String,
    @field:SerializedName("signin") val signin: String,
    @field:SerializedName("password") val password: String,
    @field:SerializedName("email") val email: String,
    @field:SerializedName("permGuestList") var permGuestList: Array<String>,
    @field:SerializedName("defaultEventName") var defaultEventName: String,
    @field:SerializedName("defaultEventDescription") var defaultEventDescription: String,
    @field:SerializedName("defaultEventLocation") var defaultEventLocation: String,
    @field:SerializedName("defaultTransferability") var defaultTransferability: Boolean,
    @field:SerializedName("defaultOpenTimeVisibility") var defaultOpenTimeVisibility: Boolean,
    @field:SerializedName("defaultCloseDateVisibility") var defaultCloseDateVisibility: Boolean,
    @field:SerializedName("defaultCloseTimeVisibility") var defaultCloseTimeVisibility: Boolean,
    @field:SerializedName("defaultAllStudentsVisibility") var defaultAllStudentsVisibility: Boolean,
    @field:SerializedName("allocation") var allocation: String = "Same for all members",
    @field:SerializedName("defaultPassesPerMember") var defaultPassesPerMember: Int = 2,
    @field:SerializedName("defaultPassesPerSenior") var defaultPassesPerSenior: Int = 2,
    @field:SerializedName("defaultPassesPerJunior") var defaultPassesPerJunior: Int = 2,
    @field:SerializedName("defaultPassesPerSophomore") var defaultPassesPerSophomore: Int = 2
) {
    @SerializedName("id")
    val id = 0
}