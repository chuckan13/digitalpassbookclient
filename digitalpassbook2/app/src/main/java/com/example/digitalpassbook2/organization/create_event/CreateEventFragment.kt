package com.example.digitalpassbook2.organization.create_event

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreateEventFragment : Fragment(), NumberPicker.OnValueChangeListener {

    private lateinit var startDateFormatted : String
    private lateinit var endDateFormatted : String

    private lateinit var createEventViewModel: CreateEventViewModel

    private lateinit var invitedAutoCompleteTextView : AutoCompleteTextView

    private fun handleDateTime(button : Button, date : Date, startDate : Date, endButton : Button, endDate : Date, formatter : SimpleDateFormat) {
        val format = SimpleDateFormat("M/d/yy, h:mm a", Locale.US)
        format.timeZone = TimeZone.getTimeZone("EST")
        var initialDate = date
        initialDate.hours = date.hours
        initialDate.year = date.year+1900
        button.text = format.format(date)
        button.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    date.year = year
                    date.month = month
                    date.date = dayOfMonth
                    date.hours = hourOfDay
                    date.minutes = minute
                    button.text = format.format(date)
                    if (endDate.before(startDate)) {
                        endDate.time = startDate.time
                        endButton.text = format.format(endDate)
                    }
                    startDateFormatted = formatter.format(startDate)
                    endDateFormatted = formatter.format(endDate)
                }, date.hours, date.minutes, false)
                timePickerDialog.updateTime(date.hours, date.minutes)
                timePickerDialog.show()
            }, date.year, date.month, date.day)
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.updateDate(date.year, date.month, date.date)
            datePickerDialog.show()
        }
    }

    // function for switching an element's visibility between VISIBLE and INVISIBLE
    private fun View.toggleVisibility() {
        visibility = when {
            (visibility == View.VISIBLE) -> View.INVISIBLE
            else -> View.VISIBLE
        }
    }

    private fun passesNumber(button: Button) {
        val number = button.text.toString().toInt()
        button.setOnClickListener {
            val dialog = context?.let { it1 -> Dialog(it1) }
            dialog?.setTitle("Number of Passes per Member")
            dialog?.setContentView(R.layout.number_picker_dialog)
            val cancelButton = dialog?.findViewById<Button>(R.id.cancel_button)
            val setButton = dialog?.findViewById<Button>(R.id.set_button)
            val numberPicker = dialog?.findViewById<NumberPicker>(R.id.number_picker)
            numberPicker?.maxValue = 3
            numberPicker?.minValue = 0
            numberPicker?.value = button.text.toString().toInt()
            numberPicker?.wrapSelectorWheel = false
            numberPicker?.setOnValueChangedListener(this)
            cancelButton?.setOnClickListener {
                dialog.dismiss()
            }
            setButton?.setOnClickListener {
                button.text = numberPicker?.value.toString()
                dialog.dismiss()
            }
            dialog?.show()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val doorsOpen = view.findViewById<Button>(R.id.doors_open_date_time_button)
        val doorsClose = view.findViewById<Button>(R.id.doors_close_date_time_button)
        val toggleButton = view.findViewById<Button>(R.id.option_toggle)
        val eventTitle = view.findViewById<EditText>(R.id.event_title)
        val description = view.findViewById<EditText>(R.id.description)
        val location = view.findViewById<EditText>(R.id.location)
        val passesNumber = view.findViewById<Button>(R.id.number)
        val transferability = view.findViewById<Switch>(R.id.transferability)
        val permGuestList = view.findViewById<Switch>(R.id.perm_guest_list)
        val viewableOpeningTime = view.findViewById<Switch>(R.id.viewable_opening_time)
        val viewableClosingDate = view.findViewById<Switch>(R.id.viewable_closing_date)
        val viewableClosingTime = view.findViewById<Switch>(R.id.viewable_closing_time)
        val publicEvent = view.findViewById<Switch>(R.id.public_event)


        // Doors open and close
        val startDate = Date()
        val endDate = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        startDateFormatted = formatter.format(startDate)
        endDateFormatted = formatter.format(endDate)
        handleDateTime(doorsOpen, startDate, startDate, doorsClose, endDate, formatter)
        handleDateTime(doorsClose, endDate, startDate, doorsClose, endDate, formatter)

        // Toggle visibility
        toggleButton.setOnClickListener{
            eventTitle.toggleVisibility()
            location.toggleVisibility()
            description.toggleVisibility()
            options_grid.toggleVisibility()
            if (toggleButton.text == getString(R.string.see_more)) {
                toggleButton.text = getString(R.string.see_less)
            }
            else toggleButton.text = getString(R.string.see_more)
        }

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

        // Handle passes number
        val numberButton = view.findViewById<Button>(R.id.number)
        passesNumber(numberButton)

        // Invited field passes
        val guestList : MutableList<Student?> = ArrayList()
        view.findViewById<ImageButton>(R.id.add_invited).setOnClickListener {
            createEventViewModel.doesStudentExist(invitedAutoCompleteTextView.text.toString())
            createEventViewModel.studentExists.observe(
                context as FragmentActivity,
                Observer { it1 ->
                    val studentExists = (it1 ?: return@Observer)
                    if (studentExists) {
                        createEventViewModel.getStudentFromInvited(invitedAutoCompleteTextView.text.toString())
                        createEventViewModel.student.observe(
                            context as FragmentActivity,
                            Observer { it2 ->
                                val student = (it2 ?: return@Observer)
                                guestList.add(student)
                            })
                        invitedAutoCompleteTextView.setText("")
                    }
                    else {
                        Toast.makeText(context, "The NetID " + invitedAutoCompleteTextView.text.toString() + " does not match any user", Toast.LENGTH_LONG).show()
                    }
                })
        }

        // Submit form
        view.findViewById<Button>(R.id.submit).setOnClickListener {
            // Create event
            val numberPasses = numberButton.text.toString().toInt()
            val localEvent = Event(MyOrganization.id, startDateFormatted, endDateFormatted, eventTitle.text.toString(),
                description.text.toString(), location.text.toString(), transferability.isChecked,
                viewableOpeningTime.isChecked, viewableClosingDate.isChecked,
                viewableClosingTime.isChecked, endDateFormatted, publicEvent.isChecked)
            createEventViewModel.createEvent(localEvent)

            createEventViewModel.event.observe(context as FragmentActivity, Observer { it2 ->
                val event = (it2 ?: return@Observer)

                // Member passes
                createEventViewModel.getMemberList(MyOrganization.id)
                createEventViewModel.memberList.observe(context as FragmentActivity, Observer { it1 ->
                    val memberList = (it1 ?: return@Observer)
                    memberList.forEach {
                        for (i in 0 until numberPasses) {
                            makePass(it, event)
                        }
                    }
                })

                for (student in guestList) {
                    makePass(student, event)
                }

                // Permanent guest list passes
//                if (permGuestList.isChecked) {
//                    TODO()
//                }

            })

            findNavController().navigate(R.id.navigation_home)
        }
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        Log.i("value is",""+newVal)
    }
}
