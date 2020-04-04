package com.example.digitalpassbook2.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.digitalpassbook2.MainActivity
import com.example.digitalpassbook2.Pass
import com.example.digitalpassbook2.PassService
import com.example.digitalpassbook2.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var passesListView : ListView

    private val passServe by lazy {
        PassService.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

//        view?.findViewById<Button>(R.id.signout_btn)?.setOnClickListener {
//            val intent = Intent(context, LoginActivity::class.java)
//            context?.startActivity(intent)
//        }

        val createCall: Call<List<Pass?>?>? = passServe.getByUserId(1)
        val passList: MutableList<Pass?> = ArrayList()

        createCall?.enqueue(object : Callback<List<Pass?>?> {
            override fun onResponse(call: Call<List<Pass?>?>?, response: Response<List<Pass?>?>?) {
                for (b in response?.body()!!) {
                    passList.add(b)
                }
                val adapter =
                    activity?.let { PassListAdapter(it, passList) }
                passesListView.adapter = adapter
            }
            override fun onFailure(call: Call<List<Pass?>?>?, t: Throwable?) {
                println("failure")
            }
        })

        passesListView = root.findViewById<ListView>(R.id.passes_list_view)

        return root
    }

//    fun moveToDisplayPass(pass: Pass?) {
//        val newPass = DisplayPass(pass)
//        if (newPass != null) {
//            (activity as MainActivity).moveFragment(newPass, DisplayPass.TAG)
//        }
//    }

}