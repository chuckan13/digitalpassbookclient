package com.example.digitalpassbook2.notifications

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.digitalpassbook2.MyUser
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.Util
import com.google.android.material.bottomnavigation.BottomNavigationView

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (MyUser.isOrg) inflater.inflate(R.menu.organization_toolbar_nav_menu, menu)
        else inflater.inflate(R.menu.student_toolbar_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (MyUser.isOrg) Util.onOptionsOrganization(item, context)
        else Util.onOptionsStudent(item, context)
        return super.onOptionsItemSelected(item)
    }

    private fun setNavigation(view : View) {
        val host : Int
        if (MyUser.isOrg) {
            host = R.id.organization_nav_host_fragment
        }
        else {
            host = R.id.student_nav_host_fragment
        }
        val navController = Navigation.findNavController(context as FragmentActivity, host)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity?)!!.supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar.setupWithNavController(navController)
        setHasOptionsMenu(true)
    }

}
