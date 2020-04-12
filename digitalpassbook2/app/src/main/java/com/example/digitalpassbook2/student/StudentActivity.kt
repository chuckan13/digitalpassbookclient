package com.example.digitalpassbook2.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.login.ui.LoggedInUserView
import com.example.digitalpassbook2.server.Student
import com.example.digitalpassbook2.server.StudentService
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentActivity : AppCompatActivity() {

    private val studentServe by lazy {
        StudentService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        val navView: BottomNavigationView = findViewById(R.id.student_nav_view)

        val user = intent.getParcelableExtra<LoggedInUserView>("EXTRA_PARCEL")

        val studentCall = studentServe[user.id!!]
        studentCall?.enqueue(object : Callback<Student?> {
            override fun onResponse(call: Call<Student?>?, response: Response<Student?>?) {
                MyStudent.student = response?.body()
            }

            override fun onFailure(call: Call<Student?>?, t: Throwable?) {
                println("failure")
            }

        })

        val navController = findNavController(R.id.student_nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_passbook,
            R.id.navigation_eventbook
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}
