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
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.*

class StudentEventListAdapter (private val context: Context,
                               private val studentEventList: MutableList<Event?>) : BaseAdapter() {

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
        val studentEventListViewModel = StudentEventListViewModel()
        val rowView = inflater.inflate(R.layout.adapter_student_event_list, parent, false)

        val event = getItem(position)
        val clubLogo = rowView.findViewById(R.id.event_club_logo) as ImageView
        val clubName = rowView.findViewById(R.id.event_club_name) as TextView
        val eventDate = rowView.findViewById(R.id.event_date) as TextView

        event?.orgId?.let { it1 -> studentEventListViewModel.getOrganization(it1) }
        studentEventListViewModel.organization.observe(context as FragmentActivity, Observer {
            val organization = it ?: return@Observer
            clubLogo.setImageResource(rowView.resources.getIdentifier(organization.logo, "drawable", context.packageName))
            clubName.text = organization.name
            eventDate.text = event?.startDate.toString()

//            rowView.findViewById<Button>(R.id.view_button).setOnClickListener {
//                val action =
//                    EventbookFragmentDirections.actionNavigationEventbookToNavigationViewEvent(event?.id, event?.orgId)
//                rowView.findNavController().navigate(action)
//            }

        })

        return rowView
    }

}