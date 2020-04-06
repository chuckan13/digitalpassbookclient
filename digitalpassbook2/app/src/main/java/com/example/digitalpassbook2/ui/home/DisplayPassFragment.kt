package com.example.digitalpassbook2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.digitalpassbook2.Pass
import com.example.digitalpassbook2.PassService
import com.example.digitalpassbook2.R


class DisplayPassFragment() : Fragment() {

    private val passServe by lazy {
        PassService.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_pass, container, false)
    }

    private val args: DisplayPassFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val passId = args.passArg
        val passCall = passServe.get(passId.toInt())
        val passResponse = passCall?.execute()
        val pass = passResponse?.body()

        val passName = view.findViewById(R.id.pass_name) as TextView
        passName.text = pass?.passName
    }
}
