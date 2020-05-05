package com.example.digitalpassbook2.organization.create_event

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.MyUser
import com.example.digitalpassbook2.Util
import com.example.digitalpassbook2.server.Event
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_create_event.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreateEventFragment : Fragment(), NumberPicker.OnValueChangeListener {

    private lateinit var createEventViewModel: CreateEventViewModel

    private lateinit var startDateFormatted : String
    private lateinit var endDateFormatted : String
    private lateinit var invitedAutoCompleteTextView : AutoCompleteTextView

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

    // function for switching an element's visibility between VISIBLE and INVISIBLE
    private fun View.toggleVisibility() {
        visibility = when {
            (visibility == View.VISIBLE) -> View.INVISIBLE
            else -> View.VISIBLE
        }
    }

    private fun passesNumber(button: Button) {
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

    private fun memberListPasses(numberPasses : Int, event : Event) {
        createEventViewModel.getMemberList(MyUser.id)
        createEventViewModel.memberList.observe(context as FragmentActivity, Observer { it1 ->
            val memberList = (it1 ?: return@Observer)
            memberList.forEach {
                for (i in 0 until numberPasses) {
                    createEventViewModel.makePass(it, event)
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        createEventViewModel = ViewModelProviders.of(this).get(CreateEventViewModel::class.java)
        return inflater.inflate(R.layout.fragment_create_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation(view)

        val eventTitle = view.findViewById<EditText>(R.id.event_title)
        val description = view.findViewById<EditText>(R.id.description)
        val location = view.findViewById<EditText>(R.id.location)
        val numberButton = view.findViewById<Button>(R.id.number)
        val transferability = view.findViewById<Switch>(R.id.transferability)
        val viewableOpeningTime = view.findViewById<Switch>(R.id.viewable_opening_time)
        val viewableClosingDate = view.findViewById<Switch>(R.id.viewable_closing_date)
        val viewableClosingTime = view.findViewById<Switch>(R.id.viewable_closing_time)

        createEventViewModel.getOrg(MyUser.id)
        createEventViewModel.org.observe(context as FragmentActivity, Observer {
            val organization = (it ?: return@Observer)
            eventTitle.setText(organization.defaultEventName)
            description.setText(organization.defaultEventDescription)
            location.setText(organization.defaultEventLocation)
            numberButton.text = organization.defaultPassesPerMember.toString()
            transferability.isChecked = organization.defaultTransferability
            viewableOpeningTime.isChecked = organization.defaultCloseTimeVisibility
            viewableClosingDate.isChecked = organization.defaultCloseDateVisibility
            viewableClosingTime.isChecked = organization.defaultCloseTimeVisibility
        })

        // Doors open and close
        val startDate = Date()
        val endDate = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        startDateFormatted = formatter.format(startDate)
        endDateFormatted = formatter.format(endDate)
        val doorsOpen = view.findViewById<Button>(R.id.doors_open_date_time_button)
        val doorsClose = view.findViewById<Button>(R.id.doors_close_date_time_button)
        handleDateTime(doorsOpen, startDate, startDate, doorsClose, endDate, formatter)
        handleDateTime(doorsClose, endDate, startDate, doorsClose, endDate, formatter)

        // Toggle visibility
        val toggleButton = view.findViewById<Button>(R.id.option_toggle)
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
        val studentStringList : MutableList<String> = ArrayList()
        invitedAutoCompleteTextView = view.findViewById(R.id.invited)
        createEventViewModel.getStudentList()
        createEventViewModel.studentList.observe(context as FragmentActivity, Observer { it1 ->
            val studentList = (it1 ?: return@Observer)
            // change this to eliminate studentStringList and use custom adapter
            studentList.forEach{
                if (it != null) {
                    studentStringList.add(it.netid)
                }
            }
            val adapter = activity?.let {ArrayAdapter<String>(it, android.R.layout.simple_dropdown_item_1line, studentStringList)}
            invitedAutoCompleteTextView.setAdapter(adapter)
        })
        val guestList : MutableList<String?> = ArrayList()
        view.findViewById<ImageButton>(R.id.add_invited).setOnClickListener {
            val guest = invitedAutoCompleteTextView.text.toString()
            if (guest in studentStringList) {
                guestList.add(guest)
                invitedAutoCompleteTextView.setText("")
            }
            else {
                Toast.makeText(context, "The NetID \"" + invitedAutoCompleteTextView.text.toString() + "\" does not match any user", Toast.LENGTH_LONG).show()
            }
        }

        // Handle passes number
        passesNumber(numberButton)

        // Submit form
        view.findViewById<Button>(R.id.submit).setOnClickListener {
            val dialogClickListener =
            DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        val numberPasses = numberButton.text.toString().toInt()
                        val localEvent = Event(
                            MyUser.id, startDateFormatted, endDateFormatted,
                            eventTitle.text.toString(), description.text.toString(), location.text.toString(),
                            transferability.isChecked, viewableOpeningTime.isChecked,
                            viewableClosingDate.isChecked, viewableClosingTime.isChecked, endDateFormatted,
                            allStudentsVisibility = false, bouncers = arrayOf(), numArrived = 0)
                        createEventViewModel.createEvent(localEvent)
                        createEventViewModel.event.observe(context as FragmentActivity, Observer { it2 ->
                            val event = (it2 ?: return@Observer)
                            memberListPasses(numberPasses, event)
                            createEventViewModel.guestListPasses(guestList, event)
                        })
                        findNavController().navigate(R.id.navigation_home)
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

            val builder: AlertDialog.Builder = AlertDialog.Builder(context as FragmentActivity, android.R.style.Theme_Holo_Dialog)
            builder.setMessage("Create this event?")
            builder.setPositiveButton("Yes", dialogClickListener)
            builder.setNegativeButton("No", dialogClickListener)
            builder.show()
        }
    }

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        Log.i("value is",""+newVal)
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
