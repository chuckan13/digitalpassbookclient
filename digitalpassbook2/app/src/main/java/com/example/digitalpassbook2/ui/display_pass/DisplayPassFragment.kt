package com.example.digitalpassbook2.ui.display_pass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.digitalpassbook2.server.Pass
import com.example.digitalpassbook2.server.PassService
import com.example.digitalpassbook2.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DisplayPassFragment() : Fragment() {

    private val passServe by lazy {
        PassService.create()
    }

    private val args: DisplayPassFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val passId = args.passArg

        val passCall = passServe.get(passId.toInt())
        val passName = view.findViewById(R.id.pass_name) as TextView

        passCall?.enqueue(object : Callback<Pass?> {
            override fun onResponse(call: Call<Pass?>?, response: Response<Pass?>?) {
                val pass = response?.body()
                passName.text = pass?.passName
            }

            override fun onFailure(call: Call<Pass?>?, t: Throwable?) {
                println("failure")
            }
        })
    }
}
