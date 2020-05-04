package com.example.digitalpassbook2.login.register.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.digitalpassbook2.MainActivity
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.login.login.ui.LoggedInUserView
import com.example.digitalpassbook2.login.login.ui.LoginActivity
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_scan.*


class ScanActivity : AppCompatActivity() {

    private lateinit var scannerViewModel: ScannerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scannerViewModel = ScannerViewModel()
        setContentView(R.layout.activity_scan)

        val user = intent.getParcelableExtra<LoggedInUserView>("EXTRA_PARCEL")

        scanner_button.setOnClickListener{
            val integrator = IntentIntegrator(this)
            integrator.setOrientationLocked(true)
            integrator.initiateScan()
        }
        type_button.setOnClickListener{
            var text = ""
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Enter Barcode Number:")
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            builder.setPositiveButton("OK"
            ) { _, _ ->
                text = input.text.toString()
                scan_result.text = text
                continue_button.text = getString(R.string.scan_complete)
                loading_scanner.visibility = View.VISIBLE
                continue_button.isEnabled = false
                scanner_button.isEnabled = false
                try {
                    scannerViewModel.updateBarcode(user!!.userId, text)
                }
                catch (e: Exception) {
                    Log.d("Not recognizing Bar Code as String", e.toString())
                }
            }
            builder.setNegativeButton("Cancel"
            ) { dialog, _ -> dialog.cancel() }
            builder.show()
        }
        continue_button.setOnClickListener {
            val intent: Intent = Intent(this, MainActivity::class.java)
            intent.putExtra("EXTRA_PARCEL", user)
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
                scan_result.text = result.contents
                continue_button.text = getString(R.string.scan_complete)
                loading_scanner.visibility = View.VISIBLE
                continue_button.isEnabled = false
                scanner_button.isEnabled = false
                try {
                    scannerViewModel.updateBarcode(user!!.userId, result.contents)
                }
                catch (e: Exception) {
                    Log.d("Not recognizing Bar Code as String", e.toString())
                }
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        setResult(Activity.RESULT_OK)
    }

}