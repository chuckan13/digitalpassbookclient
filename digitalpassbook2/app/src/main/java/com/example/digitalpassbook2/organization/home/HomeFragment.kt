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
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.organization.MyOrganization
import com.example.digitalpassbook2.server.Event


class HomeFragment : Fragment(), FragmentManager.OnBackStackChangedListener {

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var eventsListView : ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController(context as FragmentActivity, R.id.organization_nav_host_fragment)
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

        eventsListView = view.findViewById<ListView>(R.id.events_list_view)

        homeViewModel.getEventList(MyOrganization.id)
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

    // Menu icons are inflated just as they were with actionbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.organization_bottom_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val navigator = findNavController(
            context as FragmentActivity,
            R.id.organization_nav_host_fragment
        )
        if (id == R.id.navigation_home) {
            navigator.navigate(R.id.navigation_home)
        }
        else if (id == R.id.navigation_create_event) {
            navigator.navigate(R.id.action_navigation_home_to_navigation_create_event)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackStackChanged() {
//        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        shouldDisplayHomeUp()
    }

//    private fun shouldDisplayHomeUp() {
//        //Enable Up button only  if there are entries in the back stack
//        val canGoBack = fragmentManager?.backStackEntryCount!! > 0
//        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(canGoBack)
//    }


}