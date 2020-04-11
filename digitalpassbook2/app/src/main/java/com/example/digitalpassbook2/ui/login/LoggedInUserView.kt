package com.example.digitalpassbook2.ui.login

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * User details post authentication that is exposed to the UI
 */
@Parcelize
data class LoggedInUserView(
    val id: Int?,
    val displayName: String,
    val isClub: Boolean// data fields accessible to UI
): Parcelable
