package com.example.digitalpassbook2.login.login.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.example.digitalpassbook2.login.login.data.LoginRepository
import com.example.digitalpassbook2.login.login.data.Result

import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.login.register.ui.RegisterFormState
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        viewModelScope.launch{
            val result = loginRepository.login(username, password)

            if (result is Result.Success) {
                _loginResult.value =
                    LoginResult(
                        success = LoggedInUserView(
                            username = result.data.username,
                            userId = result.data.userId,
                            name = result.data.name,
                            logoId = result.data.logoId,
                            isOrg = result.data.isOrg
                        )
                    )
            } else if (result is Result.Error) {
                _loginResult.value = LoginResult(error = result.exception.message.toString())
            }
        }
    }

    fun loginDataChanged(username: String) {
        if (username.isBlank()) {
            Log.d("Register View Model", "Invalid Username")
            _loginForm.value = LoginFormState(usernameError = R.string.blank_username)
        }
        else if (username.contains(" ")) {
            _loginForm.value = LoginFormState(usernameError = R.string.contains_whitespace)
        }
        else if (!isLowerCase(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.username_lowercase)
        }
        else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    private fun isLowerCase(username: String): Boolean {
        for (c in username) {
            if (c.isUpperCase())
                return false
        }
        return true
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()
    }

}
