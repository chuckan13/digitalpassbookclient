package com.example.digitalpassbook2.student.display_pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.*
import kotlinx.coroutines.launch

class DisplayPassViewModel : ViewModel() {

    private val passServe by lazy {
        PassService.create()
    }

    private val organizationServe by lazy {
        OrganizationService.create()
    }

    private val _text = MutableLiveData<String>().apply {
        value = "Display Pass"
    }

    val text: LiveData<String> = _text

    private val _pass = MutableLiveData<Pass>()
    val pass: LiveData<Pass> = _pass

    fun getPass(passId : Int) {
        viewModelScope.launch {
            _pass.value = passServe.get(passId)
        }
    }

    private val _organization = MutableLiveData<Organization>()
    val organization: LiveData<Organization> = _organization

    fun getOrganization(orgId : Int) {
        viewModelScope.launch {
            _organization.value = organizationServe.get(orgId)
        }
    }
}
