package com.example.digitalpassbook2.ui.create_event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
}
