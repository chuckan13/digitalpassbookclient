package com.example.digitalpassbook2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

//        view?.findViewById<Button>(R.id.signout_btn)?.setOnClickListener {
//            val intent = Intent(context, LoginActivity::class.java)
//            context?.startActivity(intent)
//        }

        val createCall: Call<List<Pass?>?>? = passServe.getByUserId(1)
        val passList: MutableList<String?> = ArrayList()

        createCall?.enqueue(object : Callback<List<Pass?>?> {
            override fun onResponse(call: Call<List<Pass?>?>?, response: Response<List<Pass?>?>?) {
                for (b in response?.body()!!) {
                    passList.add(b?.passName)
                }
            }
            override fun onFailure(call: Call<List<Pass?>?>?, t: Throwable?) {
                println("failure")
            }
        })

        val adapter =
            activity?.let { PassListAdapter(it, passList) }
        passesListView.adapter = adapter
        passesListView = root.findViewById<ListView>(R.id.passes_list_view)

        return root
    }


}