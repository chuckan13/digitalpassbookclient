package com.example.digitalpassbook2.login.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val username: String,
    val userId: Int,
    val isOrg: Boolean
)
