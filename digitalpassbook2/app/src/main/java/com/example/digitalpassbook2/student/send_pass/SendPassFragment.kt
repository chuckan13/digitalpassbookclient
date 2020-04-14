package com.example.digitalpassbook2.student.send_pass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digitalpassbook2.*
import com.example.digitalpassbook2.server.Student

class SendPassFragment : Fragment() {

    private lateinit var sendPassViewModel: SendPassViewModel

    private lateinit var guestNameAutoCompleteTextView : AutoCompleteTextView

    private val args: SendPassFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sendPassViewModel = ViewModelProviders.of(this).get(SendPassViewModel::class.java)
        return inflater.inflate(R.layout.fragment_send_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val passId = args.passArg.toInt()

        // Take the person's input in the invited field
        guestNameAutoCompleteTextView = view.findViewById(R.id.guest_name)
        var studentList : MutableList<Student> = ArrayList()
        sendPassViewModel.studentList.observe(context as FragmentActivity, Observer {
            sendPassViewModel.getStudentList()
            studentList = (it ?: return@Observer) as MutableList<Student>
        })
        // change this to eliminate studentStringList and use custom adapter
        val studentStringList : MutableList<String> = ArrayList()
        studentList.forEach{
            studentStringList.add(it.netid)
        }
        val adapter = activity?.let {ArrayAdapter<String>(it, android.R.layout.simple_dropdown_item_1line, studentStringList)}
        guestNameAutoCompleteTextView.setAdapter(adapter)

        // Send the pass to them and navigate back to passbook
        view.findViewById<Button>(R.id.send_button_2).setOnClickListener {
            sendPassViewModel.updatePass(passId, view.findViewById<AutoCompleteTextView>(R.id.guest_name).text.toString())
            findNavController().navigate(R.id.navigation_passbook)
        }
    }
}
