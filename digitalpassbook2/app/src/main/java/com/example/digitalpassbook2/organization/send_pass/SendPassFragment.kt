package com.example.digitalpassbook2.organization.send_pass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.digitalpassbook2.*
import com.example.digitalpassbook2.server.Pass
import com.example.digitalpassbook2.server.PassService
import com.example.digitalpassbook2.server.Student
import com.example.digitalpassbook2.server.StudentService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SendPassFragment : Fragment() {

    private val passServe by lazy {
        PassService.create()
    }

    private val studentServe by lazy {
        StudentService.create()
    }

    private lateinit var guestNameAutoCompleteTextView : AutoCompleteTextView

    private val args: SendPassFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_send_pass, container, false)
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
                val adapter = activity?.let { ArrayAdapter<String>(it, android.R.layout.simple_dropdown_item_1line, studentList) }
                guestNameAutoCompleteTextView.setAdapter(adapter)
            }
            override fun onFailure(call: Call<List<Student?>?>?, t: Throwable?) {
                println("failure")
            }
        })
        guestNameAutoCompleteTextView = view.findViewById(R.id.guest_name)

        val passId = args.passArg

        view.findViewById<Button>(R.id.send_button_2).setOnClickListener {
            val passCall = passServe.get(passId.toInt())
            passCall?.enqueue(object : Callback<Pass?> {
                override fun onResponse(call: Call<Pass?>?, response: Response<Pass?>?) {
                    val pass = response?.body()
                    val studentCall = studentServe.getByNetId(view.findViewById<AutoCompleteTextView>(R.id.guest_name).text.toString())
                    studentCall?.enqueue(object : Callback<Student?> {
                        override fun onResponse(call: Call<Student?>?, response: Response<Student?>?) {
                            val student = response?.body()
                            pass?.userId = student?.id!!
                            val passCall:Call<Pass?>? = pass?.id?.let { it1 -> passServe.update(it1, pass) }
                            passCall?.enqueue(object : Callback<Pass?> {
                                override fun onResponse(call: Call<Pass?>?, response: Response<Pass?>?) {
                                    val newItem = response?.body()
                                    findNavController().navigate(R.id.navigation_home)
                                }
                                override fun onFailure(call: Call<Pass?>?, t: Throwable?) {
                                    println("failure")
                                }
                            })

                        }
                        override fun onFailure(call: Call<Student?>?, t: Throwable?) {
                            println("failure")
                        }
                    })
                }
                override fun onFailure(call: Call<Pass?>?, t: Throwable?) {
                    println("failure")
                }
            })
        }
    }
}
