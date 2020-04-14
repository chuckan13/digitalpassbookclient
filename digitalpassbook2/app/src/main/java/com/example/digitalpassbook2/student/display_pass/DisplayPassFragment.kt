package com.example.digitalpassbook2.student.display_pass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.*


class DisplayPassFragment() : Fragment() {

    private lateinit var displayPassViewModel: DisplayPassViewModel

    private val args: DisplayPassFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        displayPassViewModel = ViewModelProviders.of(this).get(DisplayPassViewModel::class.java)
        return inflater.inflate(R.layout.fragment_display_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val passId = args.passArg.toInt()
        val orgId = args.clubArg

        val orgName = view.findViewById(R.id.pass_club_name) as TextView
        val orgLogo = view.findViewById(R.id.pass_club_logo) as ImageView

        var pass : Pass? = null
        displayPassViewModel.pass.observe(context as FragmentActivity, Observer {
            displayPassViewModel.getPass(passId)
            pass = (it ?: return@Observer)
        })

        var organization : Organization? = null
        displayPassViewModel.organization.observe(context as FragmentActivity, Observer {
            displayPassViewModel.getOrganization(orgId)
            organization = (it ?: return@Observer)
        })
        orgName.text = organization?.name
        orgLogo.setImageResource(resources.getIdentifier(organization?.logo, "drawable", context?.packageName))
    }
}
