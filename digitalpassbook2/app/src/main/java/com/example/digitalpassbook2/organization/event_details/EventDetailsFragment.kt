package com.example.digitalpassbook2.organization.event_details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

import com.example.digitalpassbook2.R

class EventDetailsFragment : Fragment(), FragmentManager.OnBackStackChangedListener {

    private lateinit var eventDetailsViewModel: EventDetailsViewModel

    private lateinit var guestNameAutoCompleteTextView : AutoCompleteTextView

    private val args: EventDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        eventDetailsViewModel = ViewModelProviders.of(this).get(EventDetailsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_event_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(
            context as FragmentActivity,
            R.id.organization_nav_host_fragment
        )
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home,
            R.id.navigation_create_event
        ))
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setHasOptionsMenu(true)
        //Listen for changes in the back stack
        fragmentManager?.addOnBackStackChangedListener(this)
        //Handle when activity is recreated like on orientation Change
//        shouldDisplayHomeUp()


        val eventId = args.eventArg.toInt()

        eventDetailsViewModel.getEvent(eventId)
        eventDetailsViewModel.event.observe(context as FragmentActivity, Observer {
            val event = (it ?: return@Observer)
        })

        guestNameAutoCompleteTextView = view.findViewById(R.id.guest_name)
        eventDetailsViewModel.getStudentList()
        eventDetailsViewModel.studentList.observe(context as FragmentActivity, Observer { it1 ->
            val studentList = (it1 ?: return@Observer)

            // change this to eliminate studentStringList and use custom adapter
            val studentStringList : MutableList<String> = ArrayList()
            studentList.forEach{
                if (it != null) {
                    studentStringList.add(it.netid)
                }
            }
            val adapter = activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_dropdown_item_1line, studentStringList) }
            guestNameAutoCompleteTextView.setAdapter(adapter)
        })


        // Send the pass to them and navigate back to passbook
        view.findViewById<Button>(R.id.send_button).setOnClickListener {
            eventDetailsViewModel.createPass(eventId, guestNameAutoCompleteTextView.text.toString())
            guestNameAutoCompleteTextView.setText("")
        }

    }

    // Menu icons are inflated just as they were with actionbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.organization_bottom_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val navigator = Navigation.findNavController(
            context as FragmentActivity,
            R.id.organization_nav_host_fragment
        )
        if (id == R.id.navigation_home) {
            navigator.navigate(R.id.navigation_home)
        }
        else if (id == R.id.navigation_create_event) {
            navigator.navigate(R.id.navigation_create_event)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackStackChanged() {
//        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        shouldDisplayHomeUp()
    }
//
//    private fun shouldDisplayHomeUp() {
//        //Enable Up button only  if there are entries in the back stack
//        val canGoBack = fragmentManager?.backStackEntryCount!! > 0
//        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(canGoBack)
//    }

}
