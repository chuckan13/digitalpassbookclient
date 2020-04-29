package com.example.digitalpassbook2.login.register.ui

import com.example.digitalpassbook2.login.login.ui.LoggedInUserView

data class RegisterResult(
    val success: LoggedInUserView? = null,
    val error: String? = null
)