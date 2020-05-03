package com.example.digitalpassbook2.login.register.ui

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.login.login.ui.LoggedInUserView
import com.example.digitalpassbook2.login.register.data.RegisterRepository
import com.example.digitalpassbook2.server.StudentService
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import com.example.digitalpassbook2.login.login.data.Result
import kotlinx.coroutines.Dispatchers

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> =  _registerForm

    private val  _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> =  _registerResult

    private val studentServe by lazy {
        StudentService.create()
    }

    fun register(first: String, last: String, classYear: String, username: String, password: String) {
        // can be launched in a separate asynchronous job
        Log.d("RegisterViewModel", "register functionc called")
        viewModelScope.launch() {
            val exists = async {studentServe.checkStudentExist(username)}
            if (!exists.await()!!) {
                Log.d("Seeing background runs", "Idk what frag change does")
                val result = registerRepository.register(first, last, classYear, username, password)

                if (result is Result.Success) {
                    _registerResult.value =
                        RegisterResult(
                            success = LoggedInUserView(
                                username = result.data.username,
                                userId = result.data.userId,
                                name = result.data.name,
                                logoId = result.data.logoId,
                                isOrg = result.data.isOrg
                            )
                        )
                } else if (result is Result.Error) {
                    _registerResult.value =
                        RegisterResult(error = result.exception.message.toString())
                }
            }
            else {
                _registerResult.value = RegisterResult(error = "Username Taken")
            }
        }
    }

    fun registerDataChanged(first: String, last: String, classYear: String, username: String, password: String) {
        if (!isNameValid(first)) {
            Log.d("Register View Model", "Invalid First")
            _registerForm.value = RegisterFormState(firstError = R.string.invalid_first_name)
        }
        else if (!isNameValid(last)) {
            Log.d("Register View Model", "Invalid Last")
            _registerForm.value = RegisterFormState(lastError = R.string.invalid_last_name)
        }
        else if (!isPasswordValid(password)) {
            Log.d("Register View Model", "Invalid Password")
            _registerForm.value = RegisterFormState(passwordError = R.string.register_invalid_password)
        }
        else if (username.isBlank()) {
            Log.d("Register View Model", "Invalid Username")
            _registerForm.value = RegisterFormState(usernameError = R.string.blank_username)
        }
        else if (username.contains(" ")) {
            _registerForm.value = RegisterFormState(usernameError = R.string.contains_whitespace)
        }
        else if (!isClassYearValid(classYear)) {
            Log.d("Register View Model", "Invalid Class Year")
            _registerForm.value = RegisterFormState(classYearError = R.string.invalid_class_year)
        }
        else {
            Log.d("Register View Model", "Valid Data")
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    private fun isClassYearValid(classYear: String): Boolean {
        return if (classYear.isNotEmpty()) {
            val classYearNumeric: Int = Integer.parseInt(classYear)
            classYearNumeric in 2020..2029
        } else
            false
    }

    private fun isNameValid(name: String): Boolean {
        return name.isNotBlank()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

}
