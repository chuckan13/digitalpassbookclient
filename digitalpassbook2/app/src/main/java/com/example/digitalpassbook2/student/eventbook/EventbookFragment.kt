package com.example.digitalpassbook2.student.eventbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.*
import com.example.digitalpassbook2.student.MyStudent
import com.example.digitalpassbook2.student.eventbook.StudentEventListAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EventbookFragment : Fragment() {

    private lateinit var eventbookViewModel: EventbookViewModel

    private lateinit var eventsListView : ListView

    private val passServe by lazy {
        PassService.create()
    }

    private val organizationServe by lazy {
        OrganizationService.create()
    }

    private val eventServe by lazy {
        EventService.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        eventbookViewModel = ViewModelProviders.of(this).get(EventbookViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_eventbook, container, false)

        val eventList: MutableList<Event?> = ArrayList()
        val eventIdList: MutableList<Int> = ArrayList()
        val memberEventsCall: Call<List<Event?>?>? = organizationServe.getEvents(MyStudent.student?.orgId!!)
        memberEventsCall?.enqueue(object : Callback<List<Event?>?> {
            override fun onResponse(call: Call<List<Event?>?>?, response: Response<List<Event?>?>?) {
                for (b in response?.body()!!) {
                    eventIdList.add(b?.id!!)
                    eventList.add(b)
                }
            }
            override fun onFailure(call: Call<List<Event?>?>?, t: Throwable?) {
                println("failure")
            }
        })

        val guestEventsCall: Call<List<Pass?>?>? = passServe.getByUserId(MyStudent.student?.id!!)
        guestEventsCall?.enqueue(object : Callback<List<Pass?>?> {
            override fun onResponse(call: Call<List<Pass?>?>?, response: Response<List<Pass?>?>?) {
                for (b in response?.body()!!) {
                    if (b?.eventId !in eventIdList) {
                        eventIdList.add(b?.eventId!!)
                        val eventCall: Call<Event?>? = eventServe[b?.eventId]
                        eventCall?.enqueue(object : Callback<Event?> {
                            override fun onResponse(call: Call<Event?>?, response: Response<Event?>?) {
                                eventList.add(response?.body())
                            }
                            override fun onFailure(call: Call<Event?>?, t: Throwable?) {
                                println("failure")
                            }
                        })
                    }
                }
            }
            override fun onFailure(call: Call<List<Pass?>?>?, t: Throwable?) {
                println("failure")
            }
        })

        val sortedEventList : MutableList<Event?> = eventList.sortedWith(compareBy {it?.date}) as MutableList<Event?>

        val adapter = activity?.let { StudentEventListAdapter(it, sortedEventList) }
        eventsListView.adapter = adapter
        eventsListView = root.findViewById<ListView>(R.id.student_events_list_view)

        return root
    }

}