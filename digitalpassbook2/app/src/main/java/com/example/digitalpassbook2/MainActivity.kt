package com.example.digitalpassbook2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalpassbook2.login.login.ui.LoggedInUserView
import com.example.digitalpassbook2.login.login.ui.LoginActivity
import com.example.digitalpassbook2.server.Pass


class MainActivity : AppCompatActivity() {
    companion object {
        var studentPassList: MutableList<Pass?>? = null
        var passUpdateBoolean = true
        // index corresponds to orgID number
        val organizationLogos = arrayOf("ic_launcher_round", "ic_cannon_logo", "ic_cap_logo",
            "ic_charter_logo", "ic_cloister_logo", "ic_colonial_logo", "ic_cottage_logo",
            "ic_ivy_logo", "ic_quad_logo", "ic_terrace_logo", "ic_ti_logo", "ic_tower_logo")

        val organizationNames = arrayOf("Club", "Cannon Dial Elm Club", "Cap and Gown Club",
            "Charter Club", "Cloister Inn", "Colonial Club", "Cottage Club", "Ivy Club",
            "Quadrangle Club", "Terrace Club", "Tiger Inn", "Tower Club")
    }

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