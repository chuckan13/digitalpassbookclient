package com.example.digitalpassbook2.login.register.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.digitalpassbook2.MainActivity
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.login.login.ui.LoggedInUserView
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_scan.*

class ScanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        val user = intent.getParcelableExtra<LoggedInUserView>("EXTRA_PARCEL")

        scanner_button.setOnClickListener{
            val integrator = IntentIntegrator(this)
            integrator.setOrientationLocked(true)
            integrator.initiateScan()
        }
        continue_button.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            intent.putExtra("EXTRA_PARCEL", user)
//                Log.d("RegisterFragment", "start new activity")
            startActivity(intent)
            setResult(Activity.RESULT_OK)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result= IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                scan_result.text = getString(R.string.scan_cancelled)
            }
            else {
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_SHORT).show()
                scan_result.text = result.contents
                continue_button.text = getString(R.string.scan_complete)
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}