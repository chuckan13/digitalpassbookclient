package com.example.digitalpassbook2.organization.home

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
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.MyUser
import com.example.digitalpassbook2.Util
import com.example.digitalpassbook2.server.Event
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeFragment : Fragment(), FragmentManager.OnBackStackChangedListener {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var eventsListView : ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation(view)

        eventsListView = view.findViewById<ListView>(R.id.events_list_view)

        homeViewModel.getEventList(MyUser.id)
        homeViewModel.eventList.observe(context as FragmentActivity, Observer { it ->
            val eventList = (it ?: return@Observer)
            var sortedEventList : MutableList<Event?> = ArrayList()
            if (eventList.isNotEmpty()) {
                sortedEventList =
                    eventList.sortedWith(compareBy { it?.startDate }) as MutableList<Event?>
            }
            val adapter = activity?.let { OrganizationEventListAdapter(it, sortedEventList) }
            eventsListView.adapter = adapter
            eventsListView.visibility = View.VISIBLE
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

    override fun onBackStackChanged() {
    }

    private fun setNavigation(view : View) {
        val navController = Navigation.findNavController(
            context as FragmentActivity,
            R.id.organization_nav_host_fragment
        )
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_notifications, R.id.navigation_preferences))
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setHasOptionsMenu(true)
        fragmentManager?.addOnBackStackChangedListener(this)
        val navView: BottomNavigationView = view.findViewById(R.id.organization_nav_view)
        navView.setupWithNavController(navController)
    }

}