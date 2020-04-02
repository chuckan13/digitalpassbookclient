package com.example.digitalpassbook2.ui.create_event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateEventViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
            value = "Create Event"
    }
    val text: LiveData<String> = _text
}