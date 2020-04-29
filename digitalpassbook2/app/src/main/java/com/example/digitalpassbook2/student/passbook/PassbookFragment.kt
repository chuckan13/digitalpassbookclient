package com.example.digitalpassbook2.student.passbook

import android.os.Bundle
import android.view.*
import android.widget.ListView
import android.widget.ProgressBar
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


class PassbookFragment : Fragment(), FragmentManager.OnBackStackChangedListener {

    private lateinit var passbookViewModel: PassbookViewModel

    private lateinit var passesListView : ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        passbookViewModel = ViewModelProviders.of(this).get(PassbookViewModel::class.java)
        return inflater.inflate(R.layout.fragment_passbook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation(view)
        passesListView = view.findViewById<ListView>(R.id.student_passes_list_view)
        val progressBar = view.findViewById<ProgressBar>(R.id.loading_spinner)

        passbookViewModel.getPasses(MyUser.id)
        passbookViewModel.passes.observe(context as FragmentActivity, Observer { it ->
            val passList = (it ?: return@Observer) as MutableList<Pass?>
            var sortedPassList : MutableList<Pass?> = ArrayList()
            if (passList.isNotEmpty()) {
                sortedPassList = passList.sortedWith(compareBy {it?.date}) as MutableList<Pass?>
            }
            val adapter = activity?.let { StudentPassListAdapter(it, sortedPassList) }
            passesListView.adapter = adapter
            passesListView.visibility = View.VISIBLE
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