package com.example.digitalpassbook2.login.register.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.digitalpassbook2.MainActivity
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.login.login.ui.LoggedInUserView
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.android.synthetic.main.fragment_display_pass.*

class ScanActivity : AppCompatActivity() {

    private lateinit var scannerViewModel: ScannerViewModel

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
        scannerViewModel.scanResult.observe(this, Observer {
            val scanResult = it ?: return@Observer
            loading_scanner.visibility = View.GONE
            continue_button.isEnabled = true
            scanner_button.isEnabled = true
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result= IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                scan_result.text = getString(R.string.scan_cancelled)
            }
            else {
                val user = intent.getParcelableExtra<LoggedInUserView>("EXTRA_PARCEL")
                Toast.makeText(this, "Scanned: " + result.contents, Toast.LENGTH_SHORT).show()
                scan_result.text = result.contents
                continue_button.text = getString(R.string.scan_complete)
                loading_scanner.visibility = View.VISIBLE
                continue_button.isEnabled = false
                scanner_button.isEnabled = false
                scannerViewModel.updateBarcode(user!!.userId, result.contents)
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}