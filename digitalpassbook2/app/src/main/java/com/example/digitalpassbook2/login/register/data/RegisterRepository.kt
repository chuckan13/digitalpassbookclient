package com.example.digitalpassbook2.login.register.data

import android.util.Log
import com.example.digitalpassbook2.login.login.data.model.LoggedInUser

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class RegisterRepository(val dataSource: RegisterDataSource) {
    // store the same caching as with login once that section is finished
    suspend fun register(
        first: String,
        last: String,
        email: String,
        username: String,
        password: String): Result<LoggedInUser> {
        Log.d("RegisterRepository", "register function called")
        return dataSource.register(first, last, email, username, password)
    }
}
