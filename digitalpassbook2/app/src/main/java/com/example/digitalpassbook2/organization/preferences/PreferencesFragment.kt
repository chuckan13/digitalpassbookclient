package com.example.digitalpassbook2.organization.preferences

import android.app.Dialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.digitalpassbook2.MyUser
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.Util
import com.example.digitalpassbook2.server.Organization
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_preferences.*

class PreferencesFragment : Fragment(), FragmentManager.OnBackStackChangedListener, NumberPicker.OnValueChangeListener {

    private lateinit var preferencesViewModel: PreferencesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        preferencesViewModel = ViewModelProviders.of(this).get(PreferencesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_preferences, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNavigation(view)

        val memberSpinner: Spinner = view.findViewById(R.id.members_spinner)
        ArrayAdapter.createFromResource(context as FragmentActivity, R.array.member_passes, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            memberSpinner.adapter = adapter
        }
        preferencesViewModel.getOrganization(MyUser.id)
        preferencesViewModel.organization.observe(context as FragmentActivity, Observer {
            val organization = (it ?: return@Observer)
            allocationType(organization, memberSpinner)
            val listener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    organization.allocation = parent?.getItemAtPosition(position).toString()
                    preferencesViewModel.updateOrganization(MyUser.id, organization)
                    allocationType(organization, memberSpinner)
                }
            }
            memberSpinner.onItemSelectedListener = listener
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

    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
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

    private fun allocationType(organization : Organization, memberSpinner : Spinner) {
        val memberSpinnerArray = resources.getStringArray(R.array.member_passes)
        if (organization.allocation == memberSpinnerArray[0]) {
            memberSpinner.setSelection(0)
            passesNumber(number_passes_per_member, organization, 0)
            spots_1.visibility = View.VISIBLE
            spots_2.visibility = View.INVISIBLE
            spots_3.visibility = View.INVISIBLE
        }
        else if (organization.allocation == memberSpinnerArray[1]) {
            memberSpinner.setSelection(1)
            passesNumber(number_passes_per_senior, organization, 1)
            passesNumber(number_passes_per_junior, organization, 2)
            passesNumber(number_passes_per_sophomore, organization, 3)
            spots_1.visibility = View.INVISIBLE
            spots_2.visibility = View.VISIBLE
            spots_3.visibility = View.INVISIBLE
        }
        else {
            memberSpinner.setSelection(2)
            spots_1.visibility = View.INVISIBLE
            spots_2.visibility = View.INVISIBLE
            spots_3.visibility = View.VISIBLE
        }
    }

    private fun passesNumber(button: Button, organization: Organization, fieldType : Int) {
        if (fieldType == 0) button.text = organization.defaultPassesPerMember.toString()
        else if (fieldType == 1) button.text = organization.defaultPassesPerSenior.toString()
        else if (fieldType == 2) button.text = organization.defaultPassesPerJunior.toString()
        else button.text = organization.defaultPassesPerSophomore.toString()
        button.setOnClickListener {
            val dialog = context?.let { it1 -> Dialog(it1) }
            dialog?.setTitle("Number of Passes")
            dialog?.setContentView(R.layout.number_picker_dialog)
            val cancelButton = dialog?.findViewById<Button>(R.id.cancel_button)
            val setButton = dialog?.findViewById<Button>(R.id.set_button)
            val numberPicker = dialog?.findViewById<NumberPicker>(R.id.number_picker)
            numberPicker?.maxValue = 10
            numberPicker?.minValue = 0
            numberPicker?.value = button.text.toString().toInt()
            numberPicker?.wrapSelectorWheel = false
            numberPicker?.setOnValueChangedListener(this)
            cancelButton?.setOnClickListener {
                dialog.dismiss()
            }
            setButton?.setOnClickListener {
                button.text = numberPicker?.value.toString()
                if (fieldType == 0) organization.defaultPassesPerMember = numberPicker?.value!!
                else if (fieldType == 1) organization.defaultPassesPerSenior = numberPicker?.value!!
                else if (fieldType == 2) organization.defaultPassesPerJunior = numberPicker?.value!!
                else organization.defaultPassesPerSophomore = numberPicker?.value!!
                preferencesViewModel.updateOrganization(MyUser.id, organization)
                dialog.dismiss()
            }
            dialog?.show()
        }
    }

}
