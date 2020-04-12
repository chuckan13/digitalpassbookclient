package com.example.digitalpassbook2.organization

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.login.ui.LoggedInUserView
import com.example.digitalpassbook2.server.Organization
import com.example.digitalpassbook2.server.OrganizationService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrganizationActivity : AppCompatActivity() {

    private val organizationServe by lazy {
        OrganizationService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organization)
        val navView: BottomNavigationView = findViewById(R.id.organization_nav_view)

        val user = intent.getParcelableExtra<LoggedInUserView>("EXTRA_PARCEL")
        val organizationCall = organizationServe[user.id!!]
        organizationCall?.enqueue(object : Callback<Organization?> {
            override fun onResponse(call: Call<Organization?>?, response: Response<Organization?>?) {
                MyOrganization.organization = response?.body()
            }

            override fun onFailure(call: Call<Organization?>?, t: Throwable?) {
                println("failure")
            }

        })

        val navController = findNavController(R.id.organization_nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_create_event
        ))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

}