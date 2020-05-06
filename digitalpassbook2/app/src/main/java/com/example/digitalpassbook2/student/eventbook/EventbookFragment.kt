package com.example.digitalpassbook2.student.eventbook

import android.os.Bundle
import android.view.*
import android.widget.ListView
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.digitalpassbook2.MainActivity
import com.example.digitalpassbook2.MyUser
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.Util
import com.example.digitalpassbook2.server.Event
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_eventbook.*


class EventbookFragment : Fragment() {

    private lateinit var eventbookViewModel: EventbookViewModel

    private lateinit var eventsListView : ListView
    private lateinit var swipeRefreshLayout : SwipeRefreshLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        eventbookViewModel = ViewModelProviders.of(this).get(EventbookViewModel::class.java)
        return inflater.inflate(R.layout.fragment_eventbook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation(view)
        eventsListView = view.findViewById(R.id.student_events_list_view)
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh)

        if (MainActivity.eventUpdateBoolean) {
            refresh()
        }
        else {
            val adapter = activity?.let { StudentEventListAdapter(it, MainActivity.studentEventList!!) }
            eventsListView.adapter = adapter
            eventsListView.visibility = View.VISIBLE
        }

        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent)
        swipeRefreshLayout.setOnRefreshListener {
            refresh()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.student_toolbar_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Util.onOptionsStudent(item, context)
        if (item.itemId == R.id.menu_refresh) {
            refresh()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refresh() {
        eventbookViewModel.getEventList(MyUser.id)
        eventbookViewModel.eventList.observe(context as FragmentActivity, Observer { it ->
            MainActivity.studentEventList = (it ?: return@Observer)
            if (MainActivity.studentEventList!!.isNotEmpty()) {
                no_events.visibility = View.INVISIBLE
            }
            else {
                no_events.visibility = View.VISIBLE
            }
            val adapter = activity?.let { StudentEventListAdapter(it, MainActivity.studentEventList!!) }
            eventsListView.adapter = adapter
            eventsListView.visibility = View.VISIBLE
            swipeRefreshLayout.isRefreshing = false
        })
    }

    private fun setNavigation(view : View) {
        val navController = Navigation.findNavController(
            context as FragmentActivity,
            R.id.student_nav_host_fragment
        )
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_eventbook))
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setHasOptionsMenu(true)
//        val navView: BottomNavigationView = view.findViewById(R.id.student_nav_view)
//        navView.setupWithNavController(navController)
    }

}