package com.example.digitalpassbook2.student.eventbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.Pass
import com.example.digitalpassbook2.server.PassService
import com.example.digitalpassbook2.server.Student
import com.example.digitalpassbook2.server.StudentService
import kotlinx.coroutines.launch

class StudentEventListViewModel : ViewModel() {

    private val passServe by lazy {
        PassService.create()
    }

    private val studentServe by lazy {
        StudentService.create()
    }

    private val _passes = MutableLiveData<List<Pass?>?>()
    val passes: LiveData<List<Pass?>?> = _passes

    fun getPassNumber(eventsid: Long, userid: Int) {
        viewModelScope.launch {
            _passes.value = passServe.getByEventsStudentsId(eventsid.toInt(), userid)
        }
    }

    private val _student = MutableLiveData<Student?>()
    val student: LiveData<Student?> = _student

    fun getStudent(id: Int) {
        viewModelScope.launch {
            _student.value = studentServe.get(id)
        }
    }

}