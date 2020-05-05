package com.example.digitalpassbook2.organization.edit_event

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.Util
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class EditEventFragment : Fragment() {

    private lateinit var editEventViewModel: EditEventViewModel

    private lateinit var startDateFormatted : String
    private lateinit var endDateFormatted : String
    private lateinit var guestNameAutoCompleteTextView : AutoCompleteTextView
    private lateinit var bouncerAutoCompleteTextView : AutoCompleteTextView

    private val args: EditEventFragmentArgs by navArgs()

    private fun handleDateTime(button : Button, date : Date, startDate : Date, endButton : Button, endDate : Date, formatter : SimpleDateFormat) {
        val format = SimpleDateFormat("M/d/yy, h:mm a")
        format.timeZone = TimeZone.getTimeZone("GMT-04:00")
        button.text = format.format(date)
        button.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val timePickerDialog = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    date.year = year - 1900
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
            }, date.year+1900, date.month, date.date)
            datePickerDialog.datePicker.minDate = System.currentTimeMillis()
            datePickerDialog.updateDate(date.year+1900, date.month, date.date)
            datePickerDialog.show()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        editEventViewModel = ViewModelProviders.of(this).get(EditEventViewModel::class.java)
        return inflater.inflate(R.layout.fragment_edit_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation(view)

        guestNameAutoCompleteTextView = view.findViewById(R.id.guest_name)
        bouncerAutoCompleteTextView = view.findViewById(R.id.bouncer_name)
        editEventViewModel.getStudentList()
        val studentStringList : MutableList<String> = ArrayList()
        editEventViewModel.studentList.observe(context as FragmentActivity, Observer { it1 ->
            val studentList = (it1 ?: return@Observer)
            // change this to eliminate studentStringList and use custom adapter
            studentList.forEach{
                if (it != null) {
                    studentStringList.add(it.netid)
                }
            }
            val adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_dropdown_item_1line, studentStringList) }
            guestNameAutoCompleteTextView.setAdapter(adapter)
            bouncerAutoCompleteTextView.setAdapter(adapter)
        })

        val eventId = args.eventArg.toInt()
        editEventViewModel.getEvent(eventId)
        editEventViewModel.event.observe(context as FragmentActivity, Observer {
            val event = (it ?: return@Observer)

            val guestList : MutableList<String?> = ArrayList()
            view.findViewById<ImageButton>(R.id.send_pass_button).setOnClickListener {
                val guest = guestNameAutoCompleteTextView.text.toString()
                if (guest in studentStringList) {
                    guestList.add(guest)
                    guestNameAutoCompleteTextView.setText("")
                }
                else {
                    Toast.makeText(context, "The NetID \"" + guestNameAutoCompleteTextView.text.toString() + "\" does not match any user", Toast.LENGTH_LONG).show()
                }
            }

            val bouncerList : MutableList<String?> = ArrayList()
            view.findViewById<ImageButton>(R.id.add_bouncer_button).setOnClickListener {
                val bouncer = bouncerAutoCompleteTextView.text.toString()
                if (bouncer in studentStringList) {
                    bouncerList.add(bouncer)
                    bouncerAutoCompleteTextView.setText("")
                }
                else {
                    Toast.makeText(context, "The NetID \"" + bouncerAutoCompleteTextView.text.toString() + "\" does not match any user", Toast.LENGTH_LONG).show()
                }
            }

            val eventTitle = view.findViewById<EditText>(R.id.event_title)
            eventTitle.setText(event.name)
            val description = view.findViewById<EditText>(R.id.description)
            description.setText(event.description)
            val location = view.findViewById<EditText>(R.id.location)
            location.setText(event.location)
            val viewableOpeningTime = view.findViewById<Switch>(R.id.viewable_opening_time)
            viewableOpeningTime.isChecked = event.openTimeVisibility
            val viewableClosingDate = view.findViewById<Switch>(R.id.viewable_closing_date)
            viewableClosingDate.isChecked = event.closeDateVisibility
            val viewableClosingTime = view.findViewById<Switch>(R.id.viewable_closing_time)
            viewableClosingTime.isChecked = event.closeTimeVisibility

            // Doors open and close
            startDateFormatted = event.startDate
            endDateFormatted = event.endDate
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            val startDate = formatter.parse(event.startDate)
            val endDate = formatter.parse(event.endDate)
            val doorsOpen = view.findViewById<Button>(R.id.doors_open_date_time_button)
            val doorsClose = view.findViewById<Button>(R.id.doors_close_date_time_button)
            handleDateTime(doorsOpen, startDate, startDate, doorsClose, endDate, formatter)
            handleDateTime(doorsClose, endDate, startDate, doorsClose, endDate, formatter)

            view.findViewById<Button>(R.id.delete_event).setOnClickListener {
                val dialogClickListener =
                    DialogInterface.OnClickListener { _, which ->
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> {
                                editEventViewModel.deleteEvent(event.id)
                                findNavController().navigate(R.id.navigation_home)
                            }
                            DialogInterface.BUTTON_NEGATIVE -> {
                            }
                        }
                    }

                val builder: AlertDialog.Builder = AlertDialog.Builder(context as FragmentActivity, android.R.style.Theme_Holo_Dialog)
                builder.setTitle("Delete this event?")
                builder.setMessage("This change is permanent.")
                builder.setPositiveButton("Yes", dialogClickListener)
                builder.setNegativeButton("No", dialogClickListener)
                builder.show()
            }

            view.findViewById<Button>(R.id.submit).setOnClickListener {
                val dialogClickListener =
                    DialogInterface.OnClickListener { _, which ->
                        when (which) {
                            DialogInterface.BUTTON_POSITIVE -> {
                                event.startDate = startDateFormatted
                                event.endDate = endDateFormatted
                                event.name = eventTitle.text.toString()
                                event.location = location.text.toString()
                                event.description = description.text.toString()
                                event.openTimeVisibility = viewableOpeningTime.isChecked
                                event.closeDateVisibility = viewableClosingDate.isChecked
                                event.closeTimeVisibility = viewableClosingTime.isChecked
                                editEventViewModel.guestListPasses(guestList, event)
                                editEventViewModel.bouncerList(bouncerList, event)
                                editEventViewModel.updateEvent(event.id, event)
                                findNavController().navigate(R.id.navigation_home)
                            }
                            DialogInterface.BUTTON_NEGATIVE -> {
                            }
                        }
                    }

                val builder: AlertDialog.Builder = AlertDialog.Builder(context as FragmentActivity, android.R.style.Theme_Holo_Dialog)
                builder.setTitle("Update this event?")
                builder.setPositiveButton("Yes", dialogClickListener)
                builder.setNegativeButton("No", dialogClickListener)
                builder.show()
            }
        })

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.organization_toolbar_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Util.onOptionsOrganization(item, context)
        return super.onOptionsItemSelected(item)
    }

    private fun setNavigation(view : View) {
        val navController = Navigation.findNavController(
            context as FragmentActivity,
            R.id.organization_nav_host_fragment
        )
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_preferences))
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setHasOptionsMenu(true)
        val navView: BottomNavigationView = view.findViewById(R.id.organization_nav_view)
        navView.setupWithNavController(navController)
    }

}
