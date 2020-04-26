package com.example.digitalpassbook2.organization.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.organization.MyOrganization
import com.example.digitalpassbook2.server.*
import java.text.SimpleDateFormat


class OrganizationEventListAdapter (private val context: Context,
                                    private val eventList: List<Event?>) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return eventList.size
    }

    override fun getItem(position: Int): Event? {
        return eventList[position]
    }

    override fun getItemId(position: Int): Long {
        return eventList[position]?.id?.toLong()!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val organizationEventListViewModel = OrganizationEventListViewModel()
        val rowView = inflater.inflate(R.layout.adapter_organization_event_list, parent, false)

        val clubLogo = rowView.findViewById(R.id.event_club_logo) as ImageView
        clubLogo.setImageResource(rowView.resources.getIdentifier(MyOrganization.logo, "drawable", context.packageName))

        val clubName = rowView.findViewById(R.id.event_club_name) as TextView
        clubName.text = MyOrganization.name

        val eventDate = rowView.findViewById(R.id.event_date) as TextView
        val event = getItem(position)
        val format1 = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ")
        val format2 = SimpleDateFormat("MM/dd")
        val dateStart = event?.startDate
        val formatDateStart = dateStart?.substring(5,10)
        eventDate.text = formatDateStart

        rowView.findViewById<ImageButton>(R.id.details_button).setOnClickListener {
            val action =
                HomeFragmentDirections.actionNavigationHomeToNavigationEventDetails(getItemId(position))
            rowView.findNavController().navigate(action)
        }

        val numberInvited = rowView.findViewById<RelativeLayout>(R.id.number_invited).findViewById<TextView>(R.id.imageViewText)
        event?.id?.let { it1 -> organizationEventListViewModel.getStudents(it1) }
        organizationEventListViewModel.students.observe(context as FragmentActivity, Observer {
            val students = it ?: return@Observer
            numberInvited.text = students.size.toString()
        })

        val numberSpots = rowView.findViewById<RelativeLayout>(R.id.number_spots).findViewById<TextView>(R.id.imageViewText)
        event?.id?.let { it1 -> organizationEventListViewModel.getPasses(it1) }
        organizationEventListViewModel.passes.observe(context as FragmentActivity, Observer {
            val passes = it ?: return@Observer
            numberSpots.text = passes.size.toString()
        })

        val numberArrived = rowView.findViewById<RelativeLayout>(R.id.number_arrived).findViewById<TextView>(R.id.imageViewText)
        numberArrived.text = 0.toString()

        return rowView
    }

}
