package com.example.digitalpassbook2.login.register.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.login.login.ui.LoggedInUserView
import com.example.digitalpassbook2.organization.OrganizationActivity
import com.example.digitalpassbook2.server.Student
import com.example.digitalpassbook2.student.StudentActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterActivity : AppCompatActivity() {
    companion object {
        var globalUserData: LoggedInUserView? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Register Activity", "post action bar, pre content view")
        setContentView(R.layout.activity_register)
        Log.d("Register Activity", "post content view, pre navController")
    }
}

