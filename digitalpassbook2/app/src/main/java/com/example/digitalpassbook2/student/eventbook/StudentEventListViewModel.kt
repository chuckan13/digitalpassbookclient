package com.example.digitalpassbook2.student.eventbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.*
import kotlinx.coroutines.launch

class StudentEventListViewModel : ViewModel() {

    private val organizationServe by lazy {
        OrganizationService.create()
    }

    private val _organization = MutableLiveData<Organization>()
    val organization: LiveData<Organization> = _organization

    fun getOrganization(id: Int) {
        viewModelScope.launch {
            _organization.value = organizationServe.get(id)
        }
    }

}