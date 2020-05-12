package com.example.digitalpassbook2.student.eventbook

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.*
import kotlinx.coroutines.launch

class EventbookViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Events"
    }
    val text: LiveData<String> = _text

    private val eventServe by lazy {
        EventService.create()
    }

    private val organizationServe by lazy {
        OrganizationService.create()
    }

    private val passServe by lazy {
        PassService.create()
    }

    private val studentServe by lazy {
        StudentService.create()
    }

    private val _eventList = MutableLiveData<MutableList<Event?>>()
    val eventList: LiveData<MutableList<Event?>> = _eventList

    fun getEventList(id: Int) {
        viewModelScope.launch {
            Log.d("EventbookViewModel", "getStudent by ID")
            val student = studentServe.get(id)
            Log.d("EventbookViewModel", "getEvents by orgID")
            // Member Events
            val events = student?.orgId?.let { organizationServe.getEvents(it) } as MutableList<Event?>
            val eventIds : MutableList<Int> = ArrayList()
            events.forEach {
                if (it != null) {
                    eventIds.add(it.id)
                }
            }
            Log.d("EventbookViewModel", "getPasses by user ID")
            val passList = passServe.getByUserId(id)
            // Guest Events
            passList?.forEach {
                if (it != null) {
                    try {
                        val guestEvent = it.eventId.let { it1 -> eventServe.get(it1) }
                        if (guestEvent != null) {
                            if (guestEvent.orgId != student.orgId && guestEvent.id !in eventIds) {
                                events.add(guestEvent)
                                eventIds.add(guestEvent.id)
                            }
                        }
                    }
                    catch (exception : Exception) {}
                }
            }
            // Bouncer Events
            if (student.bouncingEvents != null) {
                student.bouncingEvents!!.forEach {
                    try {
                        val bouncerEvent = eventServe.get(it)
                        if (bouncerEvent != null) {
                            if (bouncerEvent.id !in eventIds) {
                                events.add(bouncerEvent)
                                eventIds.add(bouncerEvent.id)
                            }
                        }
                    }
                    catch (exception : Exception) {}
                }
            }

            var sortedEventList: MutableList<Event?> = ArrayList()
            if (events.isNotEmpty()) {
                sortedEventList =
                    events.sortedWith(compareBy { it?.startDate }) as MutableList<Event?>
            }
            _eventList.value = sortedEventList
        }
    }

}