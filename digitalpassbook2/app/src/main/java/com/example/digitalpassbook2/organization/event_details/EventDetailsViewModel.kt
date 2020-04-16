package com.example.digitalpassbook2.organization.event_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.organization.MyOrganization
import com.example.digitalpassbook2.server.*
import kotlinx.coroutines.launch

class EventDetailsViewModel : ViewModel() {

    private val eventServe by lazy {
        EventService.create()
    }

    private val passServe by lazy {
        PassService.create()
    }

    private val studentServe by lazy {
        StudentService.create()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Event Details"
    }

    val text: LiveData<String> = _text

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    fun getEvent(eventId : Int) {
        viewModelScope.launch {
            _event.value = eventServe.get(eventId)
        }
    }

    private val _studentList = MutableLiveData<List<Student?>>()
    val studentList: LiveData<List<Student?>> = _studentList

    fun getStudentList() {
        viewModelScope.launch {
            _studentList.value = studentServe.getall()
        }
    }

    fun createPass(eventId: Int, netid : String) {
        viewModelScope.launch {
            val student = studentServe.getByNetId(netid)
            val event = eventServe.get(eventId)
            val pass = student?.id?.let { Pass(MyOrganization.id, it, eventId, "", false, null, event?.date) }
            if (pass != null) {
                passServe.create(pass)
            }
        }
    }

}
