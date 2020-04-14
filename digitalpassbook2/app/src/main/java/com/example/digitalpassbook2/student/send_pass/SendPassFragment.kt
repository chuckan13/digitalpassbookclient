package com.example.digitalpassbook2.student.send_pass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digitalpassbook2.*
import com.example.digitalpassbook2.server.PassService
import com.example.digitalpassbook2.server.StudentService

class SendPassFragment : Fragment() {

    private lateinit var sendPassViewModel: SendPassViewModel

    private val passServe by lazy {
        PassService.create()
    }

    private val studentServe by lazy {
        StudentService.create()
    }

    private lateinit var guestNameAutoCompleteTextView : AutoCompleteTextView

    private val args: SendPassFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sendPassViewModel = ViewModelProviders.of(this).get(SendPassViewModel::class.java)
        return inflater.inflate(R.layout.fragment_send_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val studentList: MutableList<String> = ArrayList()
        sendPassViewModel.getStudentList(studentList)

        val adapter = activity?.let {
            ArrayAdapter<String>(
                it,
                android.R.layout.simple_dropdown_item_1line,
                studentList
            )
        }
        guestNameAutoCompleteTextView = view.findViewById(R.id.guest_name)
        guestNameAutoCompleteTextView.setAdapter(adapter)

        val passId = args.passArg

        view.findViewById<Button>(R.id.send_button_2).setOnClickListener {
            sendPassViewModel.updatePass(passId.toInt(), view.findViewById<AutoCompleteTextView>(R.id.guest_name).text.toString())
            findNavController().navigate(R.id.navigation_passbook)
        }
    }
}
