package com.example.digitalpassbook2.organization.edit_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.MyUser
import com.example.digitalpassbook2.server.*
import kotlinx.coroutines.launch

class EditEventViewModel : ViewModel() {

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
        value = "Edit Event"
    }

    val text: LiveData<String> = _text

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    fun getEvent(eventId : Int) {
        viewModelScope.launch {
            _event.value = eventServe.get(eventId)
        }
    }

    fun updateEvent(eventId : Int, localEvent : Event) {
        viewModelScope.launch {
            eventServe.update(eventId, localEvent)
        }
    }

    fun deleteEvent(eventId : Int) {
        viewModelScope.launch {
            eventServe.delete(eventId)
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
            val pass = student?.id?.let { event?.startDate?.let { it1 ->
                Pass(MyUser.id, it, eventId, it1, arrayOf<String>(), isLocked=false) } }
            if (pass != null) {
                passServe.create(pass)
            }
        }
    }

    fun guestListPasses(guestList : MutableList<String?>, event : Event) {
        viewModelScope.launch {
            try {
                guestList.forEach {
                    if (it != null) {
                        createPass(event.id, it)
                    }
                }
            }
            catch (exception : Exception) {
                println("getStudentFromInvited")
            }
        }
    }

    fun bouncerList(bouncerList : MutableList<String?>, event : Event) {
        viewModelScope.launch {
            try {
                val bouncers : MutableList<String> = ArrayList()
                if (event.bouncers != null) {
                    event.bouncers.forEach {
                        bouncers.add(it)
                    }
                }
                bouncerList.forEach { it2 ->
                    if (it2 != null) {
                        bouncers.add(it2)
                        val student = studentServe.getByNetId(it2)
                        val events : MutableList<Int> = ArrayList()
                        student?.bouncingEvents?.forEach {
                            events.add(it)
                        }
                        events.add(event.id)
                        student?.bouncingEvents = events.toTypedArray()
                        if (student != null) {
                            studentServe.update(student.id, student)
                        }
                    }
                }
                event.bouncers = bouncers.toTypedArray()
                eventServe.update(event.id, event)
            }
            catch (exception : Exception) {
                println("getStudentFromInvited")
            }
        }
    }

}
