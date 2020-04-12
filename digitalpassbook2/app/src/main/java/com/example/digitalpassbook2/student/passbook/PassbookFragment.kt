package com.example.digitalpassbook2.student.passbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.digitalpassbook2.server.Pass
import com.example.digitalpassbook2.server.PassService
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.student.MyStudent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PassbookFragment : Fragment() {

    private lateinit var passbookViewModel: PassbookViewModel

    private lateinit var passesListView : ListView

    private val passServe by lazy {
        PassService.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        passbookViewModel = ViewModelProviders.of(this).get(PassbookViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_passbook, container, false)

        val createCall: Call<List<Pass?>?>? = passServe.getByUserId(MyStudent.id)
        val passList: MutableList<Pass?> = ArrayList()

        createCall?.enqueue(object : Callback<List<Pass?>?> {
            override fun onResponse(call: Call<List<Pass?>?>?, response: Response<List<Pass?>?>?) {
                for (b in response?.body()!!) {
                    passList.add(b)
                }
                val adapter = activity?.let { StudentPassListAdapter(it, passList) }
                passesListView.adapter = adapter
            }
            override fun onFailure(call: Call<List<Pass?>?>?, t: Throwable?) {
                println("failure")
            }
        })

        passesListView = root.findViewById<ListView>(R.id.student_passes_list_view)

        return root
    }

}