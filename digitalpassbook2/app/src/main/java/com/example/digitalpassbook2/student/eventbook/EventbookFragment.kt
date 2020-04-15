package com.example.digitalpassbook2.student.eventbook

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

//        eventbookViewModel.getMemberEventList(MyStudent.id)
//        eventbookViewModel.memberEventList.observe(context as FragmentActivity, Observer { it1 ->
//            val memberEventList = ((it1 ?: return@Observer) as MutableList<Event?>)
////            val sortedEventList : MutableList<Event?> = memberEventList.sortedWith(compareBy {it?.date}) as MutableList<Event?>
//            val adapter = activity?.let { StudentEventListAdapter(it, memberEventList) }
//            eventsListView.adapter = adapter
//        })

        eventbookViewModel.getGuestEventList(MyStudent.id)
        eventbookViewModel.guestEventList.observe(context as FragmentActivity, Observer { it1 ->
            val guestEventList = ((it1 ?: return@Observer) as MutableList<Event?>)
//            val sortedEventList : MutableList<Event?> = guestEventList.sortedWith(compareBy {it?.date}) as MutableList<Event?>
            val adapter = activity?.let { StudentEventListAdapter(it, guestEventList) }
            eventsListView.adapter = adapter
        })

//        eventbookViewModel.getFullEventList(MyStudent.id)
//        eventbookViewModel.fullEventList.observe(context as FragmentActivity, Observer { it1 ->
//            val fullEventList = ((it1 ?: return@Observer) as MutableList<Event?>)
////            val sortedFullEventList : MutableList<Event?> = fullEventList.sortedWith(compareBy {it?.date}) as MutableList<Event?>
//            val adapter = activity?.let { StudentEventListAdapter(it, fullEventList) }
//            eventsListView.adapter = adapter
//        })

    }

}