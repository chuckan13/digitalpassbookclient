package com.example.digitalpassbook2.organization.create_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.digitalpassbook2.*
import com.example.digitalpassbook2.organization.MyOrganization
import com.example.digitalpassbook2.server.*

class CreateEventFragment : Fragment() {

    private lateinit var createEventViewModel: CreateEventViewModel

    private lateinit var invitedAutoCompleteTextView : AutoCompleteTextView

    private fun makePass(student : Student?, event: Event) {
        val pass = student?.id?.let { it2 -> Pass(MyOrganization.id, it2, event.id, event.name, false, null, null) }
        if (pass != null) {
            createEventViewModel.createPass(pass)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        createEventViewModel = ViewModelProviders.of(this).get(CreateEventViewModel::class.java)
        return inflater.inflate(R.layout.fragment_create_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get input from invited field
        invitedAutoCompleteTextView = view.findViewById(R.id.invited)
        createEventViewModel.getStudentList()
        createEventViewModel.studentList.observe(context as FragmentActivity, Observer { it1 ->
            val studentList = (it1 ?: return@Observer)

            // change this to eliminate studentStringList and use custom adapter
            val studentStringList : MutableList<String> = ArrayList()
            studentList.forEach{
                if (it != null) {
                    studentStringList.add(it.netid)
                }
            }
            val adapter = activity?.let {ArrayAdapter<String>(it, android.R.layout.simple_dropdown_item_1line, studentStringList)}
            invitedAutoCompleteTextView.setAdapter(adapter)
        })

        // Submit form
        view.findViewById<Button>(R.id.submit).setOnClickListener {
            // Create event
            val eventTitle = view.findViewById<EditText>(R.id.event_title)
            val date = view.findViewById<EditText>(R.id.date)
            val startTime = view.findViewById<EditText>(R.id.start_time)
            val endTime = view.findViewById<EditText>(R.id.end_time)
            val location = view.findViewById<EditText>(R.id.location)
            val description = view.findViewById<EditText>(R.id.description)
            val event = Event(MyOrganization.id, eventTitle.text.toString(), description.text.toString(),
                date.text.toString(), startTime.text.toString(), endTime.text.toString(), location.text.toString(), false, false, false, false) // replace false values with switch values
            createEventViewModel.createEvent(event)

            // Member passes
            val numberPasses = view.findViewById<EditText>(R.id.number).text.toString().toInt()
            createEventViewModel.getMemberList(MyOrganization.id)
            createEventViewModel.memberList.observe(context as FragmentActivity, Observer { it1 ->
                val memberList = (it1 ?: return@Observer)
                memberList.forEach {
                    for (i in 0 until numberPasses) {
                        makePass(it, event)
                    }
                }
            })
            // Invited field passes
            createEventViewModel.getStudentFromInvited(invitedAutoCompleteTextView.text.toString())
            createEventViewModel.student.observe(context as FragmentActivity, Observer { it1 ->
                val student = (it1 ?: return@Observer)
                makePass(student, event)
            })

            findNavController().navigate(R.id.navigation_home)
        }
    }
}
