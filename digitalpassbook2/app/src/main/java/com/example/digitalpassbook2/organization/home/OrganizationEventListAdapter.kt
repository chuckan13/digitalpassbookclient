package com.example.digitalpassbook2.organization.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.organization.MyOrganization
import com.example.digitalpassbook2.server.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrganizationEventListAdapter (private val context: Context,
                                    private val eventList: MutableList<Event?>) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val organizationServe by lazy {
        OrganizationService.create()
    }

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

        val event = getItem(position)
        val eventId = getItemId(position)

        val clubName = rowView.findViewById(R.id.event_club_name) as TextView
        val clubLogo = rowView.findViewById(R.id.event_club_logo) as ImageView
        val eventDate = rowView.findViewById(R.id.event_date) as TextView
        eventDate.text = event?.date
        val organizationCall = organizationServe[MyOrganization.id]
        organizationCall?.enqueue(object : Callback<Organization?> {
            override fun onResponse(call: Call<Organization?>?, response: Response<Organization?>?) {
                val club = response?.body()
                clubName.text = club?.name
                clubLogo.setImageResource(rowView.resources.getIdentifier(club?.logo, "drawable", context.packageName))
//                rowView.findViewById<Button>(R.id.view_button).setOnClickListener {
//                    val action =
//                        HomeFragmentDirections.actionNavigationHomeToNavigationEditEvent(eventId)
//                    rowView.findNavController().navigate(action)
//                }
            }
            override fun onFailure(call: Call<Organization?>?, t: Throwable?) {
                println("failure")
            }
        })

        return rowView
    }

}
