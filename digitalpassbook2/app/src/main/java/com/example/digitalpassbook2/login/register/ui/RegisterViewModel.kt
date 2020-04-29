package com.example.digitalpassbook2.login.register.ui

import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.login.login.ui.LoggedInUserView
import com.example.digitalpassbook2.login.register.data.RegisterRepository
import com.example.digitalpassbook2.login.register.data.Result
import com.example.digitalpassbook2.server.StudentService
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class RegisterViewModel(private val registerRepository: RegisterRepository) : ViewModel() {

    private val _registerForm = MutableLiveData<RegisterFormState>()
    val registerFormState: LiveData<RegisterFormState> =  _registerForm

    private val  _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> =  _registerResult

    private val studentServe by lazy {
        StudentService.create()
    }

    fun register(first: String, last: String, email: String, username: String, password: String) {
        // can be launched in a separate asynchronous job
        Log.d("RegisterViewModel", "register functionc called")
        viewModelScope.launch{
            coroutineScope {
                val exists = async {studentServe.checkStudentExist(username)}
                if (!exists.await()!!) {
                    Log.d("Seeing background runs", "Idk what frag change does")
                    val result = registerRepository.register(first, last, email, username, password)
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
    }

    fun registerDataChanged(first: String, last: String, email: String, username: String, password: String) {
        if (!isUserNameValid(username)) {
            _registerForm.value = RegisterFormState(usernameError = R.string.invalid_username)
        }
        else if (!isEmailValid(email)) {
            _registerForm.value = RegisterFormState(emailError = R.string.invalid_email)
        }
        else if (!isNameValid(first)) {
            _registerForm.value = RegisterFormState(firstError = R.string.invalid_first_name)
        }
        else if (!isNameValid(last)) {
            _registerForm.value = RegisterFormState(lastError = R.string.invalid_last_name)
        }
        else if (!isPasswordValid(password)) {
            _registerForm.value = RegisterFormState(passwordError = R.string.register_invalid_password)
        }
        else {
            _registerForm.value = RegisterFormState(isDataValid = true)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isNameValid(name: String): Boolean {
        return name.isNotBlank()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length > 7
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()
        // add uniqueness check as well
    }

}
