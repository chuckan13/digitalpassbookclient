package com.example.digitalpassbook2.student.eventbook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.*

class StudentEventListAdapter (private val context: Context,
                               private val studentEventList: MutableList<Event?>) : BaseAdapter() {

    private lateinit var studentEventListViewModel: StudentEventListViewModel

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return studentEventList.size
    }

    override fun getItem(position: Int): Event? {
        return studentEventList[position]
    }

    override fun getItemId(position: Int): Long {
        return studentEventList[position]?.id?.toLong()!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        studentEventListViewModel = ViewModelProviders.of(context as FragmentActivity).get(StudentEventListViewModel::class.java)
        val rowView = inflater.inflate(R.layout.adapter_student_event_list, parent, false)

        val event = getItem(position)

        var organization : Organization? = null
        studentEventListViewModel.organization.observe(context, Observer {
            event?.orgId?.let { it1 -> studentEventListViewModel.getOrganization(it1) }
            organization = it ?: return@Observer
        })

        val clubLogo = rowView.findViewById(R.id.event_club_logo) as ImageView
        clubLogo.setImageResource(rowView.resources.getIdentifier(organization?.logo, "drawable", context.packageName))

        val clubName = rowView.findViewById(R.id.event_club_name) as TextView
        clubName.text = organization?.name

        val eventDate = rowView.findViewById(R.id.event_date) as TextView
        eventDate.text = event?.date

//        rowView.findViewById<Button>(R.id.view_button).setOnClickListener {
//            // fill this in
//        }

        return rowView
    }

}