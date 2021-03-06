package com.example.digitalpassbook2.student.send_pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.EventService
import com.example.digitalpassbook2.server.PassService
import com.example.digitalpassbook2.server.Student
import com.example.digitalpassbook2.server.StudentService
import kotlinx.coroutines.launch

class SendPassViewModel : ViewModel() {

    private val passServe by lazy {
        PassService.create()
    }

    private val eventServe by lazy {
        EventService.create()
    }

    private val studentServe by lazy {
        StudentService.create()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Send Pass"
    }

    val text: LiveData<String> = _text

    private val _studentList = MutableLiveData<List<Student?>>()
    val studentList: LiveData<List<Student?>> = _studentList

    fun getStudentList() {
        viewModelScope.launch {
            _studentList.value = studentServe.getall()
        }
    }

    fun updatePass(passId: Int, netid: String) {
        viewModelScope.launch {
            val pass = passServe.get(passId)
            val event = pass?.eventId?.let { eventServe.get(it) }
            val student = studentServe.getByNetId(netid)
            if (pass != null && student != null && event != null) {
                pass.userId = student.id
                if (student.orgId != pass.orgId && !event.transferability) {
                    pass.isLocked = true
                }
                passServe.update(pass.id, pass)
            }
        }
    }
}
