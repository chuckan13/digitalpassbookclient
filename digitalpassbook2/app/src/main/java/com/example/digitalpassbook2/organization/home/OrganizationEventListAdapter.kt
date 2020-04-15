package com.example.digitalpassbook2.organization.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.*


class OrganizationEventListAdapter (private val context: Context,
                                    private val eventList: MutableList<Event?>,
                                    private val organization: Organization?) : BaseAdapter() {

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
        val rowView = inflater.inflate(R.layout.adapter_organization_event_list, parent, false)

        val clubLogo = rowView.findViewById(R.id.event_club_logo) as ImageView
        clubLogo.setImageResource(rowView.resources.getIdentifier(organization?.logo, "drawable", context.packageName))

        val clubName = rowView.findViewById(R.id.event_club_name) as TextView
        clubName.text = organization?.name

        val eventDate = rowView.findViewById(R.id.event_date) as TextView
        val event = getItem(position)
        eventDate.text = event?.date

//        rowView.findViewById<Button>(R.id.view_button).setOnClickListener {
//            val action =
//                HomeFragmentDirections.actionNavigationHomeToNavigationEditEvent(getItemId(position))
//            rowView.findNavController().navigate(action)
//        }

        return rowView
    }

}