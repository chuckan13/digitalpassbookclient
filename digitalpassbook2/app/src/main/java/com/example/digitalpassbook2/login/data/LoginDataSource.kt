package com.example.digitalpassbook2.login.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.digitalpassbook2.server.Organization
import com.example.digitalpassbook2.server.OrganizationService
import com.example.digitalpassbook2.server.Student
import com.example.digitalpassbook2.server.StudentService
import com.example.digitalpassbook2.login.data.model.LoggedInUser
import com.example.digitalpassbook2.server.OrganizationService.Companion.organization
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import com.example.digitalpassbook2.server.StudentService.Companion.student
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    private val studentServe by lazy {
        StudentService.create()
    }

    private val organizationServe by lazy {
        OrganizationService.create()
    }

    private suspend fun verifyOrg(username: String, password: String): Boolean? {
        return organizationServe.checkPasswordOfOrg(username, password)
    }

    private suspend fun verifyStudent(username: String, password: String): Boolean? {
        return studentServe.checkStudentPassword(username, password)
    }

    private fun studentOrOrg (isOrg: Boolean?, isStudent: Boolean?, username: String): Result<LoggedInUser> {
        return when {
            isOrg!! -> {
                val user = LoggedInUser(username, isOrg = true)
                Result.Success(user)
            }
            isStudent!! -> {
                val user = LoggedInUser(username, isOrg = false)
                Result.Success(user)
            }
            else -> {
                Result.Error(IOException("Invalid Username or Password"))
            }
        }
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
                coroutineScope {
                    val deferredOrg =
                        async { verifyOrg(username, password) }
                    val deferredStudent =
                        async { verifyStudent(username, password) }

                    studentOrOrg(deferredOrg.await(), deferredStudent.await(), username)
            }
        } catch (e: Throwable) {
            Result.Error(
                IOException(e.message)
            )
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

