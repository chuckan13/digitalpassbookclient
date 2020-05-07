package com.example.digitalpassbook2.student.scan_pass

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.InputType
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.Util
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_scan_pass.*


class ScanPassFragment : Fragment() {

    private lateinit var scanPassViewModel: ScanPassViewModel
    private lateinit var swipeRefreshLayout : SwipeRefreshLayout

    private val args: ScanPassFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_scan_pass, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        scanPassViewModel = ViewModelProviders.of(this)[ScanPassViewModel::class.java]
        setNavigation(view)
        val scannerButton = view.findViewById<Button>(R.id.scanner_button)
        val typeButton = view.findViewById<Button>(R.id.type_button)
        scannerButton.setOnClickListener{
            val integrator = IntentIntegrator.forSupportFragment(this)
            integrator.setOrientationLocked(true)
            integrator.initiateScan()
        }

        typeButton.setOnClickListener{
            val builder: AlertDialog.Builder? = context?.let { it1 -> AlertDialog.Builder(it1) }
            builder?.setTitle("Enter Barcode Number:")
            val input = EditText(context)
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder?.setView(input)
            builder?.setPositiveButton("OK"
            ) { _, _ ->
                val text = input.text.toString()
                val eventId = args.eventArg.toInt()
                scanPassViewModel.scanPass(eventId, text)
            }
            builder?.setNegativeButton("Cancel"
            ) { dialog, _ -> dialog.cancel() }
            builder?.show()
        }

        scanPassViewModel.onList.observe(context as FragmentActivity, Observer {
            val onList = it ?: return@Observer
            val scanResult = rootview.findViewById(R.id.scan_result) as ImageView
            if (onList) {
                scanResult.setImageResource(R.drawable.ic_approve)
            }
            else {
                scanResult.setImageResource(R.drawable.ic_cancel)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result= IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                scan_result.setImageResource(R.drawable.ic_cancel)
            }
            else {
                val eventId = args.eventArg.toInt()
                scanPassViewModel.scanPass(eventId, result.contents)
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
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
        val scanResult = rootview.findViewById(R.id.scan_result) as ImageView
        scanResult.setImageResource(android.R.color.transparent)
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
