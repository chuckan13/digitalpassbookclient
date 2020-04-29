package com.example.digitalpassbook2.login.register.ui
//
//import android.content.Intent
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.view.inputmethod.EditorInfo
//import android.widget.*
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentActivity
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import com.example.digitalpassbook2.R
//import com.example.digitalpassbook2.student.StudentActivity
//
//class RegisterFragmentInProgress : Fragment() {
//
//    private lateinit var registerViewModel: RegisterViewModel
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        registerViewModel = ViewModelProviders.of(this, RegisterViewModelFactory())
//            .get(RegisterViewModel::class.java)
//        return inflater.inflate(R.layout.fragment_register_in_progress, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // finding the input fields
//        val first = view.findViewById<EditText>(R.id.first_name)
//        val last = view.findViewById<EditText>(R.id.last_name)
//        val email = view.findViewById<EditText>(R.id.email)
//        val username = view.findViewById<EditText>(R.id.username)
//        val password = view.findViewById<EditText>(R.id.password)
//        val loading = view.findViewById<ProgressBar>(R.id.loading_register)
//        val submit = view.findViewById<Button>(R.id.submit)
//
//        registerViewModel.registerFormState.observe(context as FragmentActivity, Observer {
//            val registerState = it ?: return@Observer
//            // disable register button unless both username / password is valid
//            submit.isEnabled = registerState.isDataValid
//            if (registerState.firstError != null) {
//                first.error = getString(registerState.firstError)
//            }
//            if (registerState.lastError != null) {
//                last.error = getString(registerState.lastError)
//            }
//            if (registerState.emailError != null) {
//                email.error = getString(registerState.emailError)
//            }
//            if (registerState.usernameError != null) {
//                username.error = getString(registerState.usernameError)
//            }
//            if (registerState.passwordError != null) {
//                password.error = getString(registerState.passwordError)
//            }
//        })
//
//        registerViewModel.registerResult.observe(context as FragmentActivity, Observer {
//            val registerResult = it ?: return@Observer
//            Log.d("Register Fragment", "result returned")
//            loading.visibility = View.GONE
//            if (registerResult.error != null) {
//                showRegisterFailed(registerResult.error)
//                submit.isEnabled = true
//            }
//            if (registerResult.success != null) {
//                Log.d("Register Fragment", "registerResult.success")
//                val intent: Intent = Intent(activity, StudentActivity::class.java)
//                intent.putExtra("EXTRA_PARCEL", registerResult.success)
//                activity?.startActivity(intent)
//
//            }
//        })
//
//        first.afterTextChanged {
//            registerViewModel.registerDataChanged(
//                first.text.toString(), last.text.toString(), email.text.toString(),
//                username.text.toString(), password.text.toString()
//            )
//        }
//
//        last.afterTextChanged {
//            registerViewModel.registerDataChanged(
//                first.text.toString(), last.text.toString(), email.text.toString(),
//                username.text.toString(), password.text.toString()
//            )
//        }
//
//        email.afterTextChanged {
//            registerViewModel.registerDataChanged(
//                first.text.toString(), last.text.toString(), email.text.toString(),
//                username.text.toString(), password.text.toString()
//            )
//        }
//
//        username.afterTextChanged {
//            registerViewModel.registerDataChanged(
//                first.text.toString(), last.text.toString(), email.text.toString(),
//                username.text.toString(), password.text.toString()
//            )
//        }
//
//        password.apply {
//            afterTextChanged {
//                registerViewModel.registerDataChanged(
//                    first.text.toString(), last.text.toString(), email.text.toString(),
//                    username.text.toString(), password.text.toString()
//                )
//            }
//
//            setOnEditorActionListener { _, actionId, _ ->
//                when (actionId) {
//                    EditorInfo.IME_ACTION_DONE ->
//                        registerViewModel.register(
//                            first.text.toString(),
//                            last.text.toString(),
//                            email.text.toString(),
//                            username.text.toString(),
//                            password.text.toString()
//                        )
//                }
//                false
//            }
//        }
//
//        submit.setOnClickListener {
//            Log.d("Register Fragment", "submit button clicked")
//            loading.visibility = View.VISIBLE
//            submit.isEnabled = false
//            registerViewModel.register(
//                first.text.toString(), last.text.toString(),
//                email.text.toString(), username.text.toString(), password.text.toString())
//            }
//        }
//
//    private fun showRegisterFailed(errorString: String?) {
//        Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show()
//    }
//
//    /**
//     * Extension function to simplify setting an afterTextChanged action to EditText components.
//     */
//    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
//        this.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(editable: Editable?) {
//                afterTextChanged.invoke(editable.toString())
//            }
//
//            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
//        })
//    }
//}
//
