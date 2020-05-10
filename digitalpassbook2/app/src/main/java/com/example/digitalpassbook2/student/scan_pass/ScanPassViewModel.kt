package com.example.digitalpassbook2.student.scan_pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.EventService
import kotlinx.coroutines.launch

class ScanPassViewModel : ViewModel() {

    private val eventServe by lazy {
        EventService.create()
    }

    private val _onList = MutableLiveData<Boolean>()
    val onList: LiveData<Boolean> = _onList

    fun scanPass(eventsId: Int, userBarCode: String) {
        viewModelScope.launch {
            _onList.value = eventServe.isInvited(eventsId, userBarCode)
        }
    }

}
