package com.example.digitalpassbook2.organization.create_event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.organization.MyOrganization
import com.example.digitalpassbook2.server.Event
import com.example.digitalpassbook2.server.Pass
import com.example.digitalpassbook2.server.Student
import kotlinx.android.synthetic.main.fragment_create_event.*
import java.sql.Time
import java.text.DecimalFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

class CreateEventFragment : Fragment() {

    private lateinit var createEventViewModel: CreateEventViewModel

    private lateinit var invitedAutoCompleteTextView : AutoCompleteTextView

    // function for switching an element's visibility between VISIBLE and INVISIBLE
    private fun View.toggleVisibility() {
        visibility = when {
            (visibility == View.VISIBLE) -> View.INVISIBLE
            else -> View.VISIBLE
        }
    }

    private fun makePass(student : Student?, event: Event) {
        val pass = student?.id?.let { it -> Pass(MyOrganization.id, it, event.id, event.startDate) }
        if (pass != null) {
            createEventViewModel.createPass(pass)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        createEventViewModel = ViewModelProviders.of(this).get(CreateEventViewModel::class.java)
        return inflater.inflate(R.layout.fragment_create_event, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fun handleDateTime(button : Button, date : Date, time : Time) {
            val formatter = DecimalFormat("00")
            var dateText = formatter.format(date.month) + "/" + formatter.format(date.date) + "/" + formatter.format(1900+date.year) + ", "
            var timeText = "${time.hours}:" + formatter.format(time.minutes)
            button.text = "$dateText$timeText"
            button.setOnClickListener {
                val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    date.year = year
                    date.month = month
                    date.date = dayOfMonth
                    dateText = formatter.format(date.month) + "/" + formatter.format(date.date) + "/" + formatter.format(date.year) + ", "
                    button.text = "$dateText$timeText"
                    val timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                        time.hours = hourOfDay
                        time.minutes = minute
                        timeText = "${time.hours}:" + formatter.format(time.minutes)
                        button.text = "$dateText$timeText"
                    }, time.hours, time.minutes, false)
                    timePickerDialog.show()
                }, date.year, date.month, date.day)
                datePickerDialog.datePicker.minDate = System.currentTimeMillis()
                datePickerDialog.show()
            }
        }

        val doorsOpen = view.findViewById<Button>(R.id.doors_open_date_time_button)
        val doorsClose = view.findViewById<Button>(R.id.doors_close_date_time_button)
        val toggleButton = view.findViewById<Button>(R.id.option_toggle)
        val eventTitle = view.findViewById<EditText>(R.id.event_title)
        val description = view.findViewById<EditText>(R.id.description)
        val location = view.findViewById<EditText>(R.id.location)
        val numberPasses = view.findViewById<NumberPicker>(R.id.number)
        val transferability = view.findViewById<Switch>(R.id.transferability)
        invitedAutoCompleteTextView = view.findViewById(R.id.invited)
        val permGuestList = view.findViewById<Switch>(R.id.perm_guest_list)
        val viewableOpeningTime = view.findViewById<Switch>(R.id.viewable_opening_time)
        val viewableClosingDate = view.findViewById<Switch>(R.id.viewable_closing_date)
        val viewableClosingTime = view.findViewById<Switch>(R.id.viewable_closing_time)
        val publicEvent = view.findViewById<Switch>(R.id.public_event)


        // Doors open and close
        val startDate = Date()
        val startTime = Time(System.currentTimeMillis())
        val endDate = Date()
        val endTime = Time(System.currentTimeMillis())
        handleDateTime(doorsOpen, startDate, startTime)
        handleDateTime(doorsClose, endDate, endTime)

        // Toggle visibility
        toggleButton.setOnClickListener{
            eventTitle.toggleVisibility()
            description.toggleVisibility()
            location.toggleVisibility()
            pass_options.toggleVisibility()
            guests_options.toggleVisibility()
            viewableOpeningTime.toggleVisibility()
            viewableClosingDate.toggleVisibility()
            viewableClosingTime.toggleVisibility()
            publicEvent.toggleVisibility()
            if (toggleButton.text == getString(R.string.see_more)) {
                toggleButton.text = getString(R.string.see_less)
            }
            else toggleButton.text = getString(R.string.see_more)
        }

        // Get input from invited field
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
            val event = Event(MyOrganization.id, startDate, startTime,
                endDate, endTime, eventTitle.text.toString(),
                description.text.toString(), location.text.toString(), transferability.isChecked,
                viewableOpeningTime.isChecked, viewableClosingDate.isChecked, viewableClosingTime.isChecked,
                LocalDateTime.of(endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), endTime.toInstant().atZone(ZoneId.systemDefault()).toLocalTime()),
                publicEvent.isChecked)
            createEventViewModel.createEvent(event)

//            // Member passes
//            createEventViewModel.getMemberList(MyOrganization.id)
//            createEventViewModel.memberList.observe(context as FragmentActivity, Observer { it1 ->
//                val memberList = (it1 ?: return@Observer)
//                memberList.forEach {
//                    for (i in 0 until numberPasses.value) {
//                        makePass(it, event)
//                    }
//                }
//            })
//
//            // Invited field passes
//            createEventViewModel.getStudentFromInvited(invitedAutoCompleteTextView.text.toString())
//            createEventViewModel.student.observe(context as FragmentActivity, Observer { it ->
//                val student = (it ?: return@Observer)
//                makePass(student, event)
//            })
//
//            // Permanent guest list passes
//            if (permGuestList.isChecked) {
//                TODO()
//            }

            findNavController().navigate(R.id.navigation_home)
        }
    }
}
