package com.example.digitalpassbook2.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val id: Int?,
    val displayName: String,
    val isClub: Boolean
)
