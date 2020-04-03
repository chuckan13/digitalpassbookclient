package com.example.digitalpassbook2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.digitalpassbook2.Pass
import com.example.digitalpassbook2.PassService
import com.example.digitalpassbook2.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
//import sun.text.normalizer.UTF16.append


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var passesListView : ListView

//    private val passlist: MutableList<String> = ArrayList()

    private val PassServe by lazy {
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


        val passlist: MutableList<String?> = ArrayList()
        val createCall: Call<List<Pass?>?>? = PassServe.getByUserId(1)
//            println(result)
        createCall?.enqueue(object : Callback<List<Pass?>?> {
            override fun onResponse(call: Call<List<Pass?>?>?, response: Response<List<Pass?>?>?) {
//                val newItem = response?.body()
                for (b in response?.body()!!) {
                    passlist.add(b?.passName)
                }
//                println(passlist.size)
                val adapter =
                    activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, passlist) }
                passesListView.adapter = adapter
            }

            override fun onFailure(call: Call<List<Pass?>?>?, t: Throwable?) {
                println("falure")
            }
        })

        passesListView = root.findViewById<ListView>(R.id.passes_list_view)

        val passesList = arrayOf(passlist)

//        val adapter =
//            activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, passesList) }
//        passesListView.adapter = adapter

        return root
    }


}