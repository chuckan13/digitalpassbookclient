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

    private val _student = MutableLiveData<Student>()
    val student: LiveData<Student> = _student

    fun getStudent(id: Int) {
        viewModelScope.launch {
            _student.value = studentServe.get(id)
        }
    }

    private val _memberEventList = MutableLiveData<List<Event?>>()
    val memberEventList: LiveData<List<Event?>> = _memberEventList

    fun getMemberEventList(id: Int) {
        viewModelScope.launch {
            _memberEventList.value = organizationServe.getEvents(id)
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
                guestEvents.add(guestEvent)
            }
            _guestEventList.value = guestEvents
        }
    }

}