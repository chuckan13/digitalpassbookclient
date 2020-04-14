package com.example.digitalpassbook2.student.passbook

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.*
import kotlinx.coroutines.launch

class PassbookViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Passbook"
    }
    val text: LiveData<String> = _text

    private val passServe by lazy {
        PassService.create()
    }

    private val _passes = MutableLiveData<List<Pass?>>()
    val passes: LiveData<List<Pass?>> = _passes

    fun getPasses(id: Int) {
        viewModelScope.launch {
            _passes.value = passServe.getByUserId(id)
        }
    }
}