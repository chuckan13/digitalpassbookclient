package com.example.digitalpassbook2.student.eventbook

import android.os.Bundle
import android.view.*
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.digitalpassbook2.MyUser
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.Util
import com.example.digitalpassbook2.server.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class EventbookFragment : Fragment(), FragmentManager.OnBackStackChangedListener {

    private lateinit var eventbookViewModel: EventbookViewModel

    private lateinit var eventsListView : ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        eventbookViewModel = ViewModelProviders.of(this).get(EventbookViewModel::class.java)
        return inflater.inflate(R.layout.fragment_eventbook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation(view)

        eventsListView = view.findViewById<ListView>(R.id.student_events_list_view)

        eventbookViewModel.getEventList(MyUser.id)
        eventbookViewModel.eventList.observe(context as FragmentActivity, Observer { it1 ->
            val eventList = (it1 ?: return@Observer)
            var sortedEventList : MutableList<Event?> = ArrayList()
            if (eventList.isNotEmpty()) {
                sortedEventList = eventList.sortedWith(compareBy {it?.startDate}) as MutableList<Event?>
            }
            val adapter = activity?.let { StudentEventListAdapter(it, sortedEventList) }
            eventsListView.adapter = adapter
            eventsListView.visibility = View.VISIBLE
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.student_toolbar_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Util.onOptionsStudent(item, context)
        return super.onOptionsItemSelected(item)
    }

    override fun onBackStackChanged() {
    }

    private fun setNavigation(view : View) {
        val navController = Navigation.findNavController(
            context as FragmentActivity,
            R.id.student_nav_host_fragment
        )
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_passbook, R.id.navigation_eventbook, R.id.navigation_notifications))
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setHasOptionsMenu(true)
        fragmentManager?.addOnBackStackChangedListener(this)
        val navView: BottomNavigationView = view.findViewById(R.id.student_nav_view)
        navView.setupWithNavController(navController)
    }

}