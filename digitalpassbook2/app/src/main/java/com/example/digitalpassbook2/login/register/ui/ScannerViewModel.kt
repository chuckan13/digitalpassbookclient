package com.example.digitalpassbook2.login.register.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.server.StudentService
import kotlinx.coroutines.launch

class ScannerViewModel : ViewModel() {
    private val studentServe by lazy {
        StudentService.create()
    }

    fun updateBarcode(userId: Int, barcode: Int) {
        viewModelScope.launch {
            studentServe.updateBarcode(userId, barcode)
        }
    }
}