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

        var student : Student? = null
        eventbookViewModel.student.observe(context as FragmentActivity, Observer {
            eventbookViewModel.getStudent(MyStudent.id)
            student = it ?: return@Observer
        })

        val eventList : MutableList<Event?> = ArrayList()
        val eventIdList: MutableList<Int> = ArrayList()

        var memberEventList : MutableList<Event?> = ArrayList()
        eventbookViewModel.memberEventList.observe(context as FragmentActivity, Observer {
            eventbookViewModel.getMemberEventList(student?.orgId!!)
            memberEventList = ((it ?: return@Observer) as MutableList<Event?>)
        })
        memberEventList.forEach {
            eventList.add(it)
            eventIdList.add(it!!.id)
        }

        var guestEventList : MutableList<Event?> = ArrayList()
        eventbookViewModel.guestEventList.observe(context as FragmentActivity, Observer {
            student?.id?.let { it1 -> eventbookViewModel.getGuestEventList(it1) }
            guestEventList = ((it ?: return@Observer) as MutableList<Event?>)
        })
        guestEventList.forEach {
            if (it?.id !in eventIdList) {
                eventList.add(it)
                eventIdList.add(it!!.id)
            }
        }

//        val sortedEventList : MutableList<Event?> = eventList.sortedWith(compareBy {it?.date}) as MutableList<Event?>
        val adapter = activity?.let { StudentEventListAdapter(it, eventList) }
        eventsListView.adapter = adapter

    }

}