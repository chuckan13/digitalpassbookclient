package com.example.digitalpassbook2.organization.event_details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.digitalpassbook2.R

class EventDetailsFragment : Fragment() {

    companion object {
        fun newInstance() =
            EventDetailsFragment()
    }

    private lateinit var eventDetailsViewModel: EventDetailsViewModel

    private lateinit var guestNameAutoCompleteTextView : AutoCompleteTextView

    private val args: EventDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        eventDetailsViewModel = ViewModelProviders.of(this).get(EventDetailsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_event_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

}
