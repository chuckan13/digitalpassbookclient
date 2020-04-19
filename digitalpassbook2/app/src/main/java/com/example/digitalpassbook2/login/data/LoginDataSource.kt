package com.example.digitalpassbook2.login.data

import android.util.Log
import com.example.digitalpassbook2.server.Organization
import com.example.digitalpassbook2.server.OrganizationService
import com.example.digitalpassbook2.server.Student
import com.example.digitalpassbook2.server.StudentService
import com.example.digitalpassbook2.login.data.model.LoggedInUser
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

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

    private suspend fun studentOrOrg (isOrg: Boolean?, isStudent: Boolean?, username: String): Result<LoggedInUser> {
        Log.d("isOrg", isOrg.toString())
        return when {
            isOrg!! -> {
                Log.d("IsOrg works", "here we are")
                coroutineScope {
                    val org = async { organizationServe.getOrganizationBySignin(username) }
                    val user = LoggedInUser(username = username, userId = org.await()!!.id,
                        name = org.await()!!.name, logoId = org.await()!!.logo, isOrg = true)
                    Result.Success(user)
                }
            }
            isStudent!! -> {
                coroutineScope {
                    val student = async { studentServe.getByNetId(username) }
                    val user = LoggedInUser(username = username, userId = student.await()!!.id,
                        name = student.await()!!.name, isOrg = false)
                    Result.Success(user)
                }
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

