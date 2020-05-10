package com.example.digitalpassbook2.student.display_pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.*
import kotlinx.coroutines.launch

class DisplayPassViewModel : ViewModel() {

    private val eventServe by lazy {
        EventService.create()
    }

    // gets the pass associated with a specific PassId (why do we need this here?)
    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event> = _event

    fun getEvent(eventId : Int) {
        viewModelScope.launch {
            _event.value = eventServe.get(eventId)
        }
    }

//    // gets the organization associated with a specific organization Id
//    private val _organization = MutableLiveData<Organization>()
//    val organization: LiveData<Organization> = _organization
//
//    fun getOrganization(orgId : Int) {
//        viewModelScope.launch {
//            _organization.value = organizationServe.get(orgId)
//        }
//    }
//
//    // gets the event associated with a particular eventId
//    private val _event = MutableLiveData<Event>()
//    val event: LiveData<Event> = _event
//
//    fun getEvent(eventId : Int) {
//        viewModelScope.launch {
//            _event.value = eventServe.get(eventId)
//        }
//    }
}
