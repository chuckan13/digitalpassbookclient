package com.example.digitalpassbook2.student.eventbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.Pass
import com.example.digitalpassbook2.server.PassService
import kotlinx.coroutines.launch

class StudentEventListViewModel : ViewModel() {

    private val passServe by lazy {
        PassService.create()
    }

    private val _passes = MutableLiveData<List<Pass?>?>()
    val passes: LiveData<List<Pass?>?> = _passes

    fun getPasses(id: Int) {
        viewModelScope.launch {
            _passes.value = passServe.getByUserId(id)
        }
    }

    fun getPassNumber(eventsid: Long, userid: Int) {
        viewModelScope.launch {
            _passes.value = passServe.getByEventsStudentsId(eventsid.toInt(), userid)
        }
    }
}