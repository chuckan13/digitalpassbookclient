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

    private suspend fun getOrgVar(username: String): Organization? {
        Log.d("Check 2", "getOrgVar")
        val organizationsList = organizationServe.getall()
        organizationsList?.forEach {
            if (username.equals(it?.signin, true))
                return it
        }
        return null
    }

    private suspend fun getStudentVar(username: String): Student? {
        // call to Student section of database
        Log.d("Check 1", "getStudentVar")
        val studentsList = studentServe.getall()
        studentsList?.forEach {
            if (username.equals(it?.netid, true))
                return it
        }
            return null
    }

    private fun handleAuthentication(username: String, password: String, student: Student?, organization: Organization?): Result<LoggedInUser> {
        return if (username.equals(student?.netid.toString(), true)) {
            if (password.equals(student?.password.toString())) {
                val user = LoggedInUser(student?.id, student?.netid.toString(), false)
                Result.Success(user)
            }
            else {
                Result.Error(IOException("Incorrect password"))
            }
        } else if (username.equals(organization?.signin.toString(), true)){
            if (password.equals(organization?.password.toString())) {
                val user = LoggedInUser(organization?.id, organization?.signin.toString(), true)
                Result.Success(user)
            }
            else {
                Result.Error(IOException("Incorrect password"))
            }
        } else {
            Result.Error(IOException("Username not found. To create account, click 'create account' below"))
        }
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            supervisorScope {
                val student = async { getStudentVar(username) }
                val organization = async { getOrgVar(username) }
                handleAuthentication(username, password, student?.await(), organization?.await())
            }
        } catch (e: Throwable) {
            Result.Error(IOException("Error connecting to database", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

