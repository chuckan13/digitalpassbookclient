package com.example.digitalpassbook2.organization.create_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class CreateEventViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
            value = "Create Event"
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

    private val _studentList = MutableLiveData<List<Student?>>()
    val studentList: LiveData<List<Student?>> = _studentList

    fun getStudentList() {
        viewModelScope.launch {
            _studentList.value = studentServe.getall()
        }
    }

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    fun createEvent(localEvent : Event) {
        viewModelScope.launch {
            _event.value = eventServe.create(localEvent)
        }
    }

    private val _memberList = MutableLiveData<List<Student?>>()
    val memberList: LiveData<List<Student?>> = _memberList

    fun getMemberList(id : Int) {
        viewModelScope.launch {
            val deferredMembers = async {organizationServe.getMembers(id)}
            _memberList.value = deferredMembers.await()
        }
    }

    private val _pass = MutableLiveData<Pass>()
    val pass: LiveData<Pass> = _pass

    fun createPass(localPass : Pass) {
        viewModelScope.launch {
            val deferredPass = async {passServe.create(localPass)}
            _pass.value = deferredPass.await()
        }
    }

}