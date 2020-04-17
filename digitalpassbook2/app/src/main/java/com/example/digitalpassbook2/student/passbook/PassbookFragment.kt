package com.example.digitalpassbook2.student.passbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.appcompat.widget.ButtonBarLayout
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
        return inflater.inflate(R.layout.fragment_passbook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        passesListView = view.findViewById<ListView>(R.id.student_passes_list_view)

        passbookViewModel.getPasses(MyStudent.id)
        passbookViewModel.passes.observe(context as FragmentActivity, Observer { it ->
            val passList = (it ?: return@Observer) as MutableList<Pass?>
            var sortedPassList : MutableList<Pass?> = ArrayList()
            if (passList.isNotEmpty()) {
                sortedPassList = passList.sortedWith(compareBy {it?.date}) as MutableList<Pass?>
            }
            val adapter = activity?.let { StudentPassListAdapter(it, sortedPassList) }
            passesListView.adapter = adapter
        })
    }

}