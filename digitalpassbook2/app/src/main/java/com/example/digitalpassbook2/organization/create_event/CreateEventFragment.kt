package com.example.digitalpassbook2.organization.create_event

import android.R.attr.startYear
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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

class CreateEventFragment : Fragment() {

    private lateinit var createEventViewModel: CreateEventViewModel

    private lateinit var invitedAutoCompleteTextView : AutoCompleteTextView

//    private fun DatePickerDialog.getDate(): Date {
//        val datePicker = datePicker
//        val calendar = Calendar.getInstance()
//        calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
//        return calendar.time
//    }
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    private fun TimePicker.getTime(): Time {
//        return Time(hour, minute, 0)
//    }

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

        val doorsOpen = view.findViewById<Button>(R.id.doors_open_date_time_button)
        val doorsClose = view.findViewById<Button>(R.id.doors_close_date_time_button)
        val toggleButton = view.findViewById<Switch>(R.id.option_toggle)
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
        var doorsOpenDateText = "${startDate.month}/${startDate.date}/${startDate.year}, "
        var doorsOpenTimeText = "${startTime.hours}:${startTime.minutes}"
        doorsOpen.text = "$doorsOpenDateText$doorsOpenTimeText"
        doorsOpen.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                startDate.year = year
                startDate.month = month
                startDate.date = dayOfMonth
                doorsOpenDateText = "$month/$dayOfMonth/$year, "
                val timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    startTime.hours = hourOfDay
                    startTime.minutes = minute
                    doorsOpenTimeText = "$hourOfDay:$minute"
                }, startTime.hours, startTime.minutes, false)
                timePickerDialog.show()
            }, startDate.year, startDate.month, startDate.day)
            datePickerDialog.show()
            doorsOpen.text = "$doorsOpenDateText$doorsOpenTimeText"
        }
        val endDate = Date()
        val endTime = Time(System.currentTimeMillis())
        var doorsCloseDateText = "${endDate.month}/${endDate.date}/${endDate.year}, "
        var doorsCloseTimeText = "${endTime.hours}:${endTime.minutes}"
        doorsClose.text = "$doorsCloseDateText$doorsCloseTimeText"
        doorsClose.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                endDate.year = year
                endDate.month = month
                endDate.date = dayOfMonth
                doorsCloseDateText = "$month/$dayOfMonth/$year, "
                val timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    endTime.hours = hourOfDay
                    endTime.minutes = minute
                    doorsCloseTimeText = "$hourOfDay:$minute"
                }, endTime.hours, endTime.minutes, false)
                timePickerDialog.show()
            }, endDate.year, endDate.month, endDate.day)
            datePickerDialog.show()
            doorsClose.text = "$doorsCloseDateText$doorsCloseTimeText"
        }

        // Toggle visibility
        toggleButton.setOnClickListener{
            eventTitle.toggleVisibility()
            description.toggleVisibility()
            location.toggleVisibility()
            numberPasses.toggleVisibility()
            transferability.toggleVisibility()
            invitedAutoCompleteTextView.toggleVisibility()
            permGuestList.toggleVisibility()
            viewableOpeningTime.toggleVisibility()
            viewableClosingDate.toggleVisibility()
            viewableClosingTime.toggleVisibility()
            publicEvent.toggleVisibility()
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
