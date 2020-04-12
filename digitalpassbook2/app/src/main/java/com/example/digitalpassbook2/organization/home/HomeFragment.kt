package com.example.digitalpassbook2.organization.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.digitalpassbook2.server.Pass
import com.example.digitalpassbook2.server.PassService
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.organization.MyOrganization
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var passesListView : ListView

    private val passServe by lazy {
        PassService.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val createCall: Call<List<Pass?>?>? = passServe.getByUserId(MyOrganization.id)
        val passList: MutableList<Pass?> = ArrayList()

        createCall?.enqueue(object : Callback<List<Pass?>?> {
            override fun onResponse(call: Call<List<Pass?>?>?, response: Response<List<Pass?>?>?) {
                for (b in response?.body()!!) {
                    passList.add(b)
                }
                val adapter = activity?.let { PassListAdapter(it, passList) }
                passesListView.adapter = adapter
            }
            override fun onFailure(call: Call<List<Pass?>?>?, t: Throwable?) {
                println("failure")
            }
        })

        passesListView = root.findViewById<ListView>(R.id.passes_list_view)

        return root
    }

}