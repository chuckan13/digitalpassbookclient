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
import com.example.digitalpassbook2.Event
import com.example.digitalpassbook2.EventService
import com.example.digitalpassbook2.R
import io.reactivex.disposables.Disposable
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response;
//import com.example.digitalpassbook2.EventService

class CreateEventFragment : Fragment() {

    private lateinit var createEventViewModel: CreateEventViewModel

    private var disposable: Disposable? = null

    private val EventServe by lazy {
        EventService.create()
    }


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
            var new_event = Event(1, event_title.text.toString(), description.text.toString(), date.text.toString(), start_time.text.toString(), end_time.text.toString(), "cap and gown")
//            val event_servce = EventService()
            val createCall:Call<Event?>? = EventServe.create(new_event)
//            println(result)
            createCall?.enqueue(object : Callback<Event?> {
                override fun onResponse(call: Call<Event?>?, response: Response<Event?>?) {
                    val newItem = response?.body()
                    println(newItem?.name)
                }

                override fun onFailure(call: Call<Event?>?, t: Throwable?) {
                    println("falure")
                }
            })
            findNavController().navigate(R.id.navigation_home)
        }

    }
}