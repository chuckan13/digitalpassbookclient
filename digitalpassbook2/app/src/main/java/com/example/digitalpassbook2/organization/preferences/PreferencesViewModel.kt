package com.example.digitalpassbook2.organization.preferences

import android.content.Context
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.MyUser
import com.example.digitalpassbook2.server.Organization
import com.example.digitalpassbook2.server.OrganizationService
import com.example.digitalpassbook2.server.Student
import com.example.digitalpassbook2.server.StudentService
import kotlinx.coroutines.launch

class PreferencesViewModel : ViewModel() {

    private val organizationServe by lazy {
        OrganizationService.create()
    }

    private val studentServe by lazy {
        StudentService.create()
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

    fun addMember(orgId : Int, netId : String, context : Context, memberAutoCompleteTextView: AutoCompleteTextView) {
        viewModelScope.launch {
            val student = studentServe.getByNetId(netId)
            if (student != null) {
                val name = student.name
                if (student.orgId == 0) {
                    organizationServe.addMember(orgId, netId)
                    memberAutoCompleteTextView.setText("")
                    Toast.makeText(context, "\"$name\" has been added as a member.", Toast.LENGTH_LONG).show()
                }
                else if (student.orgId != orgId) {
                    Toast.makeText(context, "The NetID \"$netId\" belongs to a different club", Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(context, "The NetID \"$netId\" already belongs to a member", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    fun removeMember(orgId : Int, netId : String, context : Context, memberAutoCompleteTextView: AutoCompleteTextView) {
        viewModelScope.launch {
            val student = studentServe.getByNetId(netId)
            if (student != null) {
                val name = student.name
                if (student.orgId == orgId) {
                    organizationServe.removeMember(orgId, netId)
                    memberAutoCompleteTextView.setText("")
                    Toast.makeText(context, "\"$name\" has been removed as a member.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "The NetID \"$netId\" does not belong to a member", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private val _studentList = MutableLiveData<List<Student?>>()
    val studentList: LiveData<List<Student?>> = _studentList

    fun getStudentList() {
        viewModelScope.launch {
            try {
                _studentList.value = studentServe.getall()
            }
            catch (exception : Exception) {
                println("getStudentList")
            }
        }
    }

    private val _student = MutableLiveData<Student?>()
    val student: LiveData<Student?> = _student

    fun getByNetId(netId: String) {
        viewModelScope.launch {
            try {
                _student.value = studentServe.getByNetId(netId)
            }
            catch (exception : Exception) {
                println("getByNetId")
            }
        }
    }

}
