package com.example.digitalpassbook2.organization.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.digitalpassbook2.server.*
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.organization.MyOrganization
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var eventsListView : ListView

    private val organizationServe by lazy {
        OrganizationService.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventsListView = view.findViewById<ListView>(R.id.events_list_view)

        val createCall: Call<List<Event?>?>? = organizationServe.getEvents(MyOrganization.id)
        val eventList: MutableList<Event?> = ArrayList()

        createCall?.enqueue(object : Callback<List<Event?>?> {
            override fun onResponse(call: Call<List<Event?>?>?, response: Response<List<Event?>?>?) {
                for (b in response?.body()!!) {
                    eventList.add(b)
                }
                val adapter = activity?.let { OrganizationEventListAdapter(it, eventList) }
                eventsListView.adapter = adapter
            }
            override fun onFailure(call: Call<List<Event?>?>?, t: Throwable?) {
                println("failure")
            }
        })
    }

}