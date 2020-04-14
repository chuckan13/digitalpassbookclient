package com.example.digitalpassbook2.student.passbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.*
import com.example.digitalpassbook2.student.MyStudent


class PassbookFragment : Fragment() {

    private lateinit var passbookViewModel: PassbookViewModel

    private lateinit var passesListView : ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        passbookViewModel = ViewModelProviders.of(this).get(PassbookViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_passbook, container, false)

        passesListView = root.findViewById<ListView>(R.id.student_passes_list_view)

        var passList : MutableList<Pass?> = ArrayList()
        passbookViewModel.passes.observe(context as FragmentActivity, Observer {
            passbookViewModel.getPasses(MyStudent.id)
            passList = (it ?: return@Observer) as MutableList<Pass?>
        })

        val adapter = activity?.let { StudentPassListAdapter(it, passList) }
        passesListView.adapter = adapter

        return root
    }

}