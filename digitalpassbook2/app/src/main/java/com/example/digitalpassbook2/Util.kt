package com.example.digitalpassbook2

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.example.digitalpassbook2.login.ui.LoginActivity

object Util {

    fun onOptionsOrganization(item: MenuItem, context : Context?) {
        val id = item.itemId
        val navigator = Navigation.findNavController(context as FragmentActivity, R.id.organization_nav_host_fragment)
        if (id == R.id.navigation_home) {
            navigator.navigate(R.id.navigation_home)
        }
        else if (id == R.id.navigation_create_event) {
            navigator.navigate(R.id.navigation_create_event)
        }
        else if (id == R.id.navigation_notifications) {
            navigator.navigate(R.id.navigation_notifications)
        }
        else if (id == R.id.navigation_preferences) {
            navigator.navigate(R.id.navigation_preferences)
        }
        else if (id == R.id.navigation_logout) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    fun onOptionsStudent(item: MenuItem, context : Context?) {
        val id = item.itemId
        val navigator = Navigation.findNavController(context as FragmentActivity, R.id.student_nav_host_fragment)
        if (id == R.id.navigation_passbook) {
            navigator.navigate(R.id.navigation_passbook)
        }
        else if (id == R.id.navigation_eventbook) {
            navigator.navigate(R.id.navigation_eventbook)
        }
        else if (id == R.id.navigation_notifications) {
            navigator.navigate(R.id.navigation_notifications)
        }
        else if (id == R.id.navigation_logout) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }
}