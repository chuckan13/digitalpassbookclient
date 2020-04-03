package com.example.digitalpassbook2.data

import com.example.digitalpassbook2.data.model.LoggedInUser
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    // still need to do real authentication/user creation for profiles. ideally create a separate register page...
    fun login(username: String, password: String): Result<LoggedInUser> {
//        try {
//            // TODO: handle loggedInUser authentication
//            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Jane Doe")
//            return Result.Success(fakeUser)
//        } catch (e: Throwable) {
//            return Result.Error(IOException("Error logging in", e))
//        }
        if (username == "jcurl") {
            val fakeUser = LoggedInUser(java.util.UUID.randomUUID().toString(), "Justin Curl")
            return Result.Success(fakeUser)
        }
        else
            return Result.Error(IOException("Error logging in"))
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

