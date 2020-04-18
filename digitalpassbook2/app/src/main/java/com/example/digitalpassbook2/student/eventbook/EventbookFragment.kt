package com.example.digitalpassbook2.student.eventbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.*
import com.example.digitalpassbook2.student.MyStudent


class EventbookFragment : Fragment() {

    private lateinit var eventbookViewModel: EventbookViewModel

    private lateinit var eventsListView : ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        eventbookViewModel = ViewModelProviders.of(this).get(EventbookViewModel::class.java)
        return inflater.inflate(R.layout.fragment_eventbook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventsListView = view.findViewById<ListView>(R.id.student_events_list_view)

        eventbookViewModel.getEventList(MyStudent.id)
        eventbookViewModel.eventList.observe(context as FragmentActivity, Observer { it1 ->
            val eventList = (it1 ?: return@Observer)
            var sortedEventList : MutableList<Event?> = ArrayList()
            if (eventList.isNotEmpty()) {
                sortedEventList = eventList.sortedWith(compareBy {it?.startDate}) as MutableList<Event?>
            }
            val adapter = activity?.let { StudentEventListAdapter(it, sortedEventList) }
            eventsListView.adapter = adapter
        })

    }

}