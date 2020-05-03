package com.example.digitalpassbook2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalpassbook2.login.login.ui.LoggedInUserView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getParcelableExtra<LoggedInUserView>("EXTRA_PARCEL")
        MyUser.id = user!!.userId
        MyUser.username = user.username
        MyUser.name = user.name
        MyUser.logo = user.logoId
        MyUser.isOrg = user.isOrg

        if (user.isOrg) {
            setContentView(R.layout.activity_organization)
        }
        else {
            setContentView(R.layout.activity_student)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}