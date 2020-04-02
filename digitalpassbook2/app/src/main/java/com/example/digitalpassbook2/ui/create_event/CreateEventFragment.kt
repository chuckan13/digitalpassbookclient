package com.example.digitalpassbook2.ui.create_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digitalpassbook2.R

class CreateEventFragment : Fragment() {

    private lateinit var createEventViewModel: CreateEventViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        createEventViewModel =
                ViewModelProviders.of(this).get(CreateEventViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_create_event, container, false)
        val textView: TextView = root.findViewById(R.id.text_create_event)
        createEventViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val event_title = view.findViewById<EditText>(R.id.event_title)
        val start_time = view.findViewById<EditText>(R.id.start_time)
        val end_time = view.findViewById<EditText>(R.id.end_time)
        val date = view.findViewById<EditText>(R.id.date)
        val description = view.findViewById<EditText>(R.id.description)

        view.findViewById<Button>(R.id.submit).setOnClickListener {
            // need to replace this with a function that handles the data and navigates home
            // but also does other stuff
            findNavController().navigate(R.id.navigation_home)
        }

    }
}
