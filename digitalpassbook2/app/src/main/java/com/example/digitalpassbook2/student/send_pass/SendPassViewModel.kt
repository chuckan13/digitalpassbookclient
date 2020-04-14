package com.example.digitalpassbook2.student.send_pass

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SendPassViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Send Pass"
    }
    val text: LiveData<String> = _text
}