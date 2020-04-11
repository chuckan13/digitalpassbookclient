package com.example.digitalpassbook2.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.digitalpassbook2.Club
import com.example.digitalpassbook2.ClubService
import com.example.digitalpassbook2.Student
import com.example.digitalpassbook2.StudentService
import com.example.digitalpassbook2.data.model.LoggedInUser
import com.example.digitalpassbook2.ui.home.PassListAdapter
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import com.example.digitalpassbook2.StudentService.Companion.student

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    private val studentServe by lazy {
        StudentService.create()
    }

    private val clubServe by lazy {
        ClubService.create()
    }

//    private fun updateClubVar(username: String) {
//        // call to Club section of database
//        val createClubCall: Call<List<Club?>?>? = clubServe.getall()
//        // connect to database to find check if student with given username exists
//        createClubCall?.enqueue(object : Callback<List<Club?>?> {
//            override fun onResponse(call: Call<List<Club?>?>?,  response: Response<List<Club?>?>?)
//            {
//                val studentsList = response?.body()!!
//                studentsList.forEach {
//                    if (username.equals(, true)) {
//                        student = it
//                    }
//                }
//            }
//            override fun onFailure(call: Call<List<Club?>?>?, t: Throwable?) {
//                println(t?.message)
//            }
//        })
//    }

    
    private fun updateStudentVar(username: String) {
        // call to Student section of database
        val createStudentCall: Call<List<Student?>?>? = studentServe.getall()
        // connect to database to find check if student with given username exists
        createStudentCall?.enqueue(object : Callback<List<Student?>?> {
            override fun onResponse(call: Call<List<Student?>?>?,  response: Response<List<Student?>?>?)
            {
                val studentsList = response?.body()!!
                studentsList.forEach {
                    if (username.equals(it?.netid, true)) {
                        student = it
                    }
                }
            }
            override fun onFailure(call: Call<List<Student?>?>?, t: Throwable?) {
                println(t?.message)
            }
        })
    }

    private fun handleStudentAuthentication(username: String, password: String): Result<LoggedInUser> {
        // Handle loggedInUser authentication
        return if (username.equals(student?.netid.toString(), false))
        {
            if (password.equals(student?.password.toString())) {
                Log.d("Password Success", "")
                val user = LoggedInUser(student?.id, student?.netid.toString(), false)
                Result.Success(user)
            }
            else {
                Log.d("UserFound, PassFailure", "")
                Result.Error(IOException("Incorrect password"))
            }
        }
        else {
            Log.d("User Not Found", "")
            Result.Error(IOException("Username not found. To create account, click 'create account' below"))
        }
    }

    // still need to do real authentication/user creation for profiles
    fun login(username: String, password: String): Result<LoggedInUser> {
        try {
            updateStudentVar(username)
            return handleStudentAuthentication(username, password)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error connecting to database", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}

