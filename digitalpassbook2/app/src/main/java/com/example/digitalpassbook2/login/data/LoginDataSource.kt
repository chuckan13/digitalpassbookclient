package com.example.digitalpassbook2.login.data

import android.util.Log
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

    private fun updateOrganizationVar(username: String) {
        val createOrganizationCall: Call<List<Organization?>?>? = organizationServe.getall()
        // connect to database to find check if student with given username exists
        Log.d("RQ Started", "call enqueued")
        createOrganizationCall?.enqueue(object : Callback<List<Organization?>?> {
            override fun onResponse(call: Call<List<Organization?>?>?,  response: Response<List<Organization?>?>?)
            {
                Log.d("RQ Inside", "executing body")
                val organizationsList = response?.body()!!
                organizationsList.forEach {
                    if (username.equals(it?.signin, true)) {
                        organization = it
                    }
                }
                Log.d("Data Username", organization?.signin.toString())
            }
            override fun onFailure(call: Call<List<Organization?>?>?, t: Throwable?) {
                println(t?.message)
            }
        })
    }

    private fun updateStudentVar(username: String) {
        // call to Student section of database
        val createStudentCall: Call<List<Student?>?>? = studentServe.getall()
        // connect to database to find check if student with given username exists

        Log.d("RQ Started", "call enqueued")
        createStudentCall?.enqueue(object : Callback<List<Student?>?> {
            override fun onResponse(call: Call<List<Student?>?>?,  response: Response<List<Student?>?>?)
            {
                Log.d("RQ Inside", "executing body")
                val studentsList = response?.body()!!
                studentsList.forEach {
                    if (username.equals(it?.netid, true)) {
                        student = it
                    }
                }
                Log.d("Data Username", student?.netid.toString())
            }
            override fun onFailure(call: Call<List<Student?>?>?, t: Throwable?) {
                println(t?.message)
            }
        })
    }

    private fun handleAuthentication(username: String, password: String): Result<LoggedInUser> {
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

    fun login(username: String, password: String): Result<LoggedInUser> {
        return try {
            Log.d("RQ Student Username Pre", student?.netid.toString())
            updateStudentVar(username)
            Log.d("RQ Stdent Username Post", student?.netid.toString())
            Log.d("RQ Username", username)
            Log.d("RQ Org Username Pre", organization?.signin.toString())
            updateOrganizationVar(username)
            Log.d("RQ Org Username Post", organization?.signin.toString())
            handleAuthentication(username, password)
        } catch (e: Throwable) {
            Result.Error(
                IOException(
                    "Error connecting to database",
                    e
                )
            )
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

