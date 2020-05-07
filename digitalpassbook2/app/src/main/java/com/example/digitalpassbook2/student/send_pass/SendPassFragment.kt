package com.example.digitalpassbook2.student.send_pass

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.digitalpassbook2.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class SendPassFragment : Fragment() {

    private lateinit var sendPassViewModel: SendPassViewModel

    private lateinit var guestNameAutoCompleteTextView : AutoCompleteTextView

    private val args: SendPassFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        sendPassViewModel = ViewModelProviders.of(this).get(SendPassViewModel::class.java)
        return inflater.inflate(R.layout.fragment_send_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation(view)
        val passId = args.passArg.toInt()

        guestNameAutoCompleteTextView = view.findViewById(R.id.guest_name)

        sendPassViewModel.getStudentList()
        sendPassViewModel.studentList.observe(context as FragmentActivity, Observer { it1 ->
            val studentList = (it1 ?: return@Observer)

            // change this to eliminate studentStringList and use custom adapter
            val studentStringList : MutableList<String> = ArrayList()
            studentList.forEach{
                if (it != null) {
                    studentStringList.add(it.netid)
                }
            }
            val adapter = activity?.let {ArrayAdapter(it, android.R.layout.simple_dropdown_item_1line, studentStringList)}
            guestNameAutoCompleteTextView.setAdapter(adapter)
        })

        // Send the pass to them and navigate back to passbook
        view.findViewById<Button>(R.id.send_button_2).setOnClickListener {
            sendPassViewModel.updatePass(passId, guestNameAutoCompleteTextView.text.toString())
            Toast.makeText(context, "Pass Sent", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.navigation_eventbook)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.student_toolbar_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Util.onOptionsStudent(item, context)
        return super.onOptionsItemSelected(item)
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
    }
}
