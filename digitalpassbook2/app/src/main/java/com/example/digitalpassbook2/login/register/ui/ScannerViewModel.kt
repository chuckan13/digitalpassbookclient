package com.example.digitalpassbook2.login.register.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.Student
import com.example.digitalpassbook2.server.StudentService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ScannerViewModel : ViewModel() {
    private val studentServe by lazy {
        StudentService.create()
    }

    private val _scanResult = MutableLiveData<Student>()
    val scanResult: LiveData<Student> =  _scanResult

    fun updateBarcode(userId: Int, barcode: String) {
        viewModelScope.launch {
            coroutineScope {
                _scanResult.value = studentServe.updateBarcode(userId, barcode)
            }
        }
    }
}