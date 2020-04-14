package com.example.digitalpassbook2.organization.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.digitalpassbook2.server.*
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.organization.MyOrganization


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var eventsListView : ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventsListView = view.findViewById<ListView>(R.id.events_list_view)

        var organization : Organization? = null
        homeViewModel.organization.observe(context as FragmentActivity, Observer {
            homeViewModel.getOrganization(MyOrganization.id)
            organization = it ?: return@Observer
        })

        var eventList : MutableList<Event?> = ArrayList()
        homeViewModel.eventList.observe(context as FragmentActivity, Observer {
            homeViewModel.getEventList(MyOrganization.id)
            eventList = (it ?: return@Observer) as MutableList<Event?>
        })

        val adapter = activity?.let { OrganizationEventListAdapter(it, eventList, organization) }
        eventsListView.adapter = adapter
    }

}