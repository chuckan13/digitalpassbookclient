package com.example.digitalpassbook2.organization.preferences

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.Organization
import com.example.digitalpassbook2.server.OrganizationService
import kotlinx.coroutines.launch

class PreferencesViewModel : ViewModel() {

    private val organizationServe by lazy {
        OrganizationService.create()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Preferences"
    }

    val text: LiveData<String> = _text

    private val _organization = MutableLiveData<Organization?>()
    val organization: LiveData<Organization?> = _organization

    fun getOrganization(id : Int) {
        viewModelScope.launch {
            _organization.value = organizationServe.get(id)
        }
    }

    fun updateOrganization(id : Int, organization: Organization?) {
        viewModelScope.launch {
            organizationServe.update(id, organization)
        }
    }

}
