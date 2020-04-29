package com.example.digitalpassbook2.login.register.data

import android.util.Log
import com.example.digitalpassbook2.login.login.data.model.LoggedInUser
import com.example.digitalpassbook2.server.StudentService
import com.example.digitalpassbook2.server.Student
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class RegisterDataSource {

    private val studentServe by lazy {
        StudentService.create()
    }

    private suspend fun createStudent (student: Student): Result<LoggedInUser> {
        return try {
            Log.d("RegisterDataSource", "pre-create student")
            studentServe.create(student)
            val user = LoggedInUser(username = student.netid, userId = student.id,
                name = student.name)
            Result.Success(user)
        }
        catch (e: Throwable) {
            Result.Error(IOException(e.message))
        }
    }

    suspend fun register(first: String, last: String, email: String, username: String, password: String): Result<LoggedInUser> {
        return try {
            // register
            val student = Student(name= "$first $last", email=email, netid=username, password=password, orgId=0)
            return createStudent(student)
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
