package com.example.digitalpassbook2.login.login.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val username: String,
    val userId: Int,
    val name: String,
    val logoId: String = "",
    val isOrg: Boolean = false
)
