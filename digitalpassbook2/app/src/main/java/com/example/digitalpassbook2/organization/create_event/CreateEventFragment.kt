package com.example.digitalpassbook2.organization.create_event

import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.digitalpassbook2.*
import com.example.digitalpassbook2.server.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateEventFragment : Fragment() {

    private lateinit var createEventViewModel: CreateEventViewModel

    private val studentServe by lazy {
        StudentService.create()
    }

    private val organizationServe by lazy {
        OrganizationService.create()
    }

    private lateinit var invitedAutoCompleteTextView : AutoCompleteTextView

    private val eventServe by lazy {
        EventService.create()
    }

    private val passServe by lazy {
        PassService.create()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        createEventViewModel = ViewModelProviders.of(this).get(CreateEventViewModel::class.java)
        return inflater.inflate(R.layout.fragment_create_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val createCall = studentServe.getall()
        val studentList: MutableList<String> = ArrayList()

        createCall?.enqueue(object : Callback<List<Student?>?> {
            override fun onResponse(call: Call<List<Student?>?>?, response: Response<List<Student?>?>?) {
                for (b in response?.body()!!) {
                    b?.netid?.let { studentList.add(it) }
                }
                val adapter = activity?.let {ArrayAdapter<String>(it, android.R.layout.simple_dropdown_item_1line, studentList)}
                invitedAutoCompleteTextView.setAdapter(adapter)
            }
            override fun onFailure(call: Call<List<Student?>?>?, t: Throwable?) {
                println("failure")
            }
        })
        invitedAutoCompleteTextView = view.findViewById(R.id.invited)

        val eventTitle = view.findViewById<EditText>(R.id.event_title)
        val date = view.findViewById<EditText>(R.id.date)
        val startTime = view.findViewById<EditText>(R.id.start_time)
        val endTime = view.findViewById<EditText>(R.id.end_time)
        val location = view.findViewById<EditText>(R.id.location)
        val description = view.findViewById<EditText>(R.id.description)

        view.findViewById<Button>(R.id.submit).setOnClickListener {
            var newEvent = Event(
                1,
                eventTitle.text.toString(),
                description.text.toString(),
                date.text.toString(),
                startTime.text.toString(),
                endTime.text.toString(),
                location.text.toString()
            )
            val createCall:Call<Event?>? = eventServe.create(newEvent)
            val enqueue = createCall?.enqueue(object : Callback<Event?> {
                override fun onResponse(call: Call<Event?>?, response: Response<Event?>?) {
                    val newItem = response?.body()
                    println(newItem?.name)
                }

                override fun onFailure(call: Call<Event?>?, t: Throwable?) {
                    println("failure")
                }
            })

            val numberPasses = view.findViewById<EditText>(R.id.number).text.toString().toInt()
            val sendMembersCall = organizationServe.getMembers(1)
            val memberList: MutableList<String> = ArrayList()

            sendMembersCall?.enqueue(object : Callback<List<Student?>?> {
                override fun onResponse(call: Call<List<Student?>?>?, response: Response<List<Student?>?>?) {
                    for (member in response?.body()!!) {
                        member?.netid?.let { memberList.add(it) }
                    }
                    // for each member in the list, create num passes and give to them
                    for (member in memberList) {
                        for (i in 0 until numberPasses) {
                            Log.d("myTag", "numberPasses")
                            val newPass =
                                Pass(
                                    1,
                                    1,
                                    newEvent.id,
                                    newEvent.name
                                )
                            val passCallback = passServe.create(newPass)

                            // just to run the damn pass creation
                            passCallback?.enqueue(object : Callback<Pass?> {
                                override fun onResponse(call: Call<Pass?>?, response: Response<Pass?>?) {
                                    val newItem = 1;
                                }
                                override fun onFailure(call: Call<Pass?>?, t: Throwable?) {
                                    println("failure")
                                }
                            })
                        }
                    }
                }
                override fun onFailure(call: Call<List<Student?>?>?, t: Throwable?) {
                    println("failure")
                }
            })
            findNavController().navigate(R.id.navigation_home)
        }
    }
}
