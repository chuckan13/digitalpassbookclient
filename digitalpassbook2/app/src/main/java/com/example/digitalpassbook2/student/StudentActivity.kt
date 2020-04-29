package com.example.digitalpassbook2.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.login.login.ui.LoggedInUserView
import com.google.android.material.bottomnavigation.BottomNavigationView

class StudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("Student Activity", "activity created, getting from intent")
        val user = intent.getParcelableExtra<LoggedInUserView>("EXTRA_PARCEL")
        MyStudent.username = user.username
        MyStudent.id = user.userId
        MyStudent.name = user.name
        Log.d("Student Activity", "my student values updated, setting content view")

        setContentView(R.layout.activity_student)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        Log.d("Student Activity", "set up nav bar at bottom")
        val navView: BottomNavigationView = findViewById(R.id.student_nav_view)
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

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
