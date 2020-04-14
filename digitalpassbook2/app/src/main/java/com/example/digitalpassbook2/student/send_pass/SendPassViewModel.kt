package com.example.digitalpassbook2.student.send_pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.widget.AutoCompleteTextView
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.PassService
import com.example.digitalpassbook2.server.StudentService
import kotlinx.coroutines.launch

class SendPassViewModel : ViewModel() {

private val passServe by lazy {
    PassService.create()
}

private val studentServe by lazy {
    StudentService.create()
}
class SendPassViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Send Pass"
    }
    val text: LiveData<String> = _text
}

    fun updatePass(passId: Int, netid: String) {
        viewModelScope.launch {
            val pass = passServe.get(passId.toInt())
            val newStudent = studentServe.getByNetId(netid)
            passServe.update(newStudent?.id!!, pass)
        }
    }

    fun getStudentList(studentList: MutableList<String>) {
        viewModelScope.launch {
            val students = studentServe.getall()
            students?.forEach {
                studentList.add(it?.netid.toString())
            }
        }
    }
}
