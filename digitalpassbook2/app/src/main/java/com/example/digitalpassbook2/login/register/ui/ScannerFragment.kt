package com.example.digitalpassbook2.login.register.ui

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.digitalpassbook2.MainActivity

import com.example.digitalpassbook2.R

class ScannerFragment : Fragment() {

    private lateinit var viewModel: ScannerViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val scanner = view.findViewById<Button>(R.id.scanner_button)
        val done = view.findViewById<TextView>(R.id.done)

        scanner.setOnClickListener {
            // put scanner code here
        }

        done.setOnClickListener {
            val intent: Intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("EXTRA_PARCEL", RegisterActivity.globalUserData)
//                Log.d("RegisterFragment", "start new activity")
            activity?.startActivity(intent)
            activity?.setResult(Activity.RESULT_OK)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ScannerViewModel::class.java)
    }
}
