package com.example.digitalpassbook2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.digitalpassbook2.Pass
import com.example.digitalpassbook2.R


class DisplayPassFragment(pass : Pass?) : Fragment() {

    private var mPass: Pass? = pass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_display_pass, container, false)
        val passName = root.findViewById(R.id.pass_name) as TextView
        passName.text = mPass?.passName
        return root
    }

    companion object {
        val TAG = DisplayPassFragment::class.java.simpleName
    }
}
