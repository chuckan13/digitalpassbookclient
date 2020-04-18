package com.example.digitalpassbook2.login.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * User details post authentication that is exposed to the UI
 */
@Parcelize
data class LoggedInUserView(
    val username: String,
    val userId: Int,
    val name: String,
    val isOrg: Boolean// data fields accessible to UI
): Parcelable
