package com.example.digitalpassbook2.organization

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.login.ui.LoggedInUserView
import com.google.android.material.bottomnavigation.BottomNavigationView


class OrganizationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getParcelableExtra<LoggedInUserView>("EXTRA_PARCEL")
        MyOrganization.id = user!!.userId
        MyOrganization.username = user.username
        MyOrganization.name = user.name
        MyOrganization.logo = user.logoId

        setContentView(R.layout.activity_organization)
//        val navView: BottomNavigationView = findViewById(R.id.organization_nav_view)

//        val navController = findNavController(R.id.organization_nav_host_fragment)
//        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}