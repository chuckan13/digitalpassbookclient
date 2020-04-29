package com.example.digitalpassbook2.login.register.ui

/**
 * Data validation state of the login form.
 */
data class RegisterFormState(
    val firstError: Int? = null,
    val lastError: Int? = null,
    val emailError: Int? = null,
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    val isDataValid: Boolean = false
)
