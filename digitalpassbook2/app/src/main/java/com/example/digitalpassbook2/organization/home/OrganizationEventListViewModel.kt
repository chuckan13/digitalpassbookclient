package com.example.digitalpassbook2.organization.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.*
import kotlinx.coroutines.launch

class OrganizationEventListViewModel : ViewModel() {

    private val eventServe by lazy {
        EventService.create()
    }

    private val _students = MutableLiveData<List<Student?>?>()
    val students: LiveData<List<Student?>?> = _students

    fun getStudents(id: Int) {
        viewModelScope.launch {
            _students.value = eventServe.getStudentsByEvent(id)
        }
    }

    private val _passes = MutableLiveData<List<Pass?>?>()
    val passes: LiveData<List<Pass?>?> = _passes

    fun getPasses(id: Int) {
        viewModelScope.launch {
            _passes.value = eventServe.getPassesByEvent(id)
        }
    }

}