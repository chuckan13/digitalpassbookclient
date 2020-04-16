package com.example.digitalpassbook2.organization.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.*
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Home"
    }
    val text: LiveData<String> = _text

    private val organizationServe by lazy {
        OrganizationService.create()
    }

    private val _eventList = MutableLiveData<List<Event?>>()
    val eventList: LiveData<List<Event?>> = _eventList

    fun getEventList(id: Int) {
        viewModelScope.launch {
            _eventList.value = organizationServe.getEvents(id)
        }
    }

}