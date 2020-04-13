package com.example.digitalpassbook2.student.display_pass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.Organization
import com.example.digitalpassbook2.server.OrganizationService
import com.example.digitalpassbook2.server.Pass
import com.example.digitalpassbook2.server.PassService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DisplayPassFragment() : Fragment() {

    private val passServe by lazy {
        PassService.create()
    }

    private val organizationServe by lazy {
        OrganizationService.create()
    }

    private val args: DisplayPassFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val passId = args.passArg
        val clubId = args.clubArg

        val clubName = view.findViewById(R.id.pass_club_name) as TextView
        val clubLogo = view.findViewById(R.id.pass_club_logo) as ImageView

        val passCall = passServe.get(passId.toInt())
        passCall?.enqueue(object : Callback<Pass?> {
            override fun onResponse(call: Call<Pass?>?, response: Response<Pass?>?) {
                val pass = response?.body()
            }

            override fun onFailure(call: Call<Pass?>?, t: Throwable?) {
                println("failure")
            }
        })

        val organizationCall = organizationServe[clubId]
        organizationCall?.enqueue(object : Callback<Organization?> {
            override fun onResponse(call: Call<Organization?>?, response: Response<Organization?>?) {
                val club = response?.body()
                clubName.text = club?.name
                clubLogo.setImageResource(resources.getIdentifier(club?.logo, "drawable", context?.packageName))
            }
            override fun onFailure(call: Call<Organization?>?, t: Throwable?) {
                println("failure")
            }
        })
    }
}
