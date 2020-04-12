package com.example.digitalpassbook2.student.passbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PassbookViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Passbook"
    }
    val text: LiveData<String> = _text
}