package com.example.digitalpassbook2.student.eventbook

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

    private val _memberEventList = MutableLiveData<List<Event?>>()
    val memberEventList: LiveData<List<Event?>> = _memberEventList

    fun getMemberEventList(id: Int) {
        viewModelScope.launch {
            val student = studentServe.get(id)
            if (student != null) {
                _memberEventList.value = organizationServe.getEvents(student.orgId)
            }
        }
    }

    private val _guestEventList = MutableLiveData<List<Event?>>()
    val guestEventList: LiveData<List<Event?>> = _guestEventList

    fun getGuestEventList(id: Int) {
        viewModelScope.launch {
            val passList = passServe.getByUserId(id)
            val guestEvents : MutableList<Event?> = ArrayList()
            passList?.forEach {
                val guestEvent = it?.eventId?.let { it1 -> eventServe.get(it1) }
                if (guestEvent !in guestEvents && guestEvent != null) {
                    guestEvents.add(guestEvent)
                }
            }
            _guestEventList.value = guestEvents
        }
    }

    private val _eventList = MutableLiveData<List<Event?>>()
    val eventList: LiveData<List<Event?>> = _eventList

    fun getEventList(id: Int) {
        viewModelScope.launch {
            var events : MutableList<Event?> = ArrayList()
            val student = studentServe.get(id)
            events = student?.orgId?.let { organizationServe.getEvents(it) } as MutableList<Event?>
            val passList = passServe.getByUserId(id)
            passList?.forEach {
                val guestEvent = it?.eventId?.let { it1 -> eventServe.get(it1) }
                if (guestEvent != null && guestEvent.orgId != student.orgId && guestEvent !in events) {
                    events.add(guestEvent)
                }
            }
            _eventList.value = events
        }
    }

}