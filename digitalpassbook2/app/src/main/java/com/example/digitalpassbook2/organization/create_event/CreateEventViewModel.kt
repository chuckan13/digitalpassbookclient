package com.example.digitalpassbook2.organization.create_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.MyUser
import com.example.digitalpassbook2.server.*
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

    private val _studentExists = MutableLiveData<Boolean?>()
    val studentExists: LiveData<Boolean?> = _studentExists

    fun doesStudentExist(netid : String) {
        viewModelScope.launch {
            try {
                _studentExists.value = studentServe.checkStudentExist(netid)
            }
            catch (exception : Exception) {
                println("doesStudentExist")
            }
        }
    }

    private val _student = MutableLiveData<Student?>()
    val student: LiveData<Student?> = _student

    fun getStudentFromInvited(netid : String) {
        viewModelScope.launch {
            try {
                _student.value = studentServe.getByNetId(netid)
            }
            catch (exception : Exception) {
                println("getStudentFromInvited")
            }
        }
    }

    private val _org = MutableLiveData<Organization?>()
    val org: LiveData<Organization?> = _org

    fun getOrg(id : Int) {
        viewModelScope.launch {
            _org.value = organizationServe.get(id)
        }
    }

    fun guestListPasses(guestList : MutableList<String?>, event : Event) {
        viewModelScope.launch {
            try {
                guestList.forEach {
                    if (it != null) {
                        val student = studentServe.getByNetId(it)
                        makePass(student, event)
                    }
                }
            }
            catch (exception : Exception) {
                println("getStudentFromInvited")
            }
        }
    }

    private val _studentList = MutableLiveData<List<Student?>>()
    val studentList: LiveData<List<Student?>> = _studentList

    fun getStudentList() {
        viewModelScope.launch {
            try {
                _studentList.value = studentServe.getall()
            }
            catch (exception : Exception) {
                println("getStudentList")
            }
        }
    }

    private val _event = MutableLiveData<Event?>()
    val event: LiveData<Event?> = _event

    fun createEvent(localEvent : Event) {
        viewModelScope.launch {
            try {
                _event.value = eventServe.create(localEvent)
            }
            catch (exception : Exception) {
                println("createEvent")
            }
        }
    }

    private val _memberList = MutableLiveData<List<Student?>>()
    val memberList: LiveData<List<Student?>> = _memberList

    fun getMemberList(id : Int) {
        viewModelScope.launch {
//            val deferredMembers = async {organizationServe.getMembers(id)}
//            _memberList.value = deferredMembers.await()
            try {
                _memberList.value = organizationServe.getMembers(id)
            }
            catch (exception : Exception) {
                println("getMemberList")
            }
        }
    }

    private fun createPass(localPass : Pass) {
        viewModelScope.launch {
//            val deferredPass = async {passServe.create(localPass)}
//            _pass.value = deferredPass.await()
            try {
                passServe.create(localPass)
            }
            catch (exception : Exception) {
                println("createPass")
                println(exception)
            }
        }
    }

    fun bouncerListPasses(bouncerList : MutableList<String?>, event : Event) {
        viewModelScope.launch {
            try {
                bouncerList.forEach {
                    if (it != null) {
                        eventServe.addBouncer(event.id, it)
                    }
                }
            }
            catch (exception : Exception) {
                println("bouncerList")
            }
        }
    }

    fun makePass(student : Student?, event: Event) {
        val pass = student?.id?.let { it -> Pass(MyUser.id, it, event.id, event.startDate, arrayOf<String>(), isLocked=false) }
        if (pass != null) {
            createPass(pass)
        }
    }

}