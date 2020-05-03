package com.example.digitalpassbook2.student.eventbook

import android.annotation.SuppressLint
import android.app.AlertDialog
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
import kotlinx.android.synthetic.main.dialog_event_details.*
import java.text.SimpleDateFormat
import kotlinx.android.synthetic.main.dialog_event_details.view.*

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

    @SuppressLint("ViewHolder")
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
            val format1 = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ")
            val format2 = SimpleDateFormat("MM/dd")
            val dateStart = event?.startDate
            val formatDateStart = dateStart?.substring(5,10)
            eventDate.text = formatDateStart

            // opens the event dialog when the row is clicked
            rowView.setOnClickListener {
                // inflate dialog with custom view
                val eventDetailsDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_event_details, null)
                // build and show dialog
                val builder = AlertDialog.Builder(context)
                    .setView(eventDetailsDialogView)
                    .setTitle("Event Details")
                val alertDialog = builder.show()

                // extracts the event detail widgets
                // val toolbartext = eventDetailsDialogView.findViewbyId(R.id.toolbar_title) as TextView
//                val doorsOpenTime = eventDetailsDialogView.findViewbyId(R.id.doors_open_date_time) as TextView
//                val doorsCloseTime = eventDetailsDialogView.findViewbyId(R.id.doors_close_date_time) as TextView
//                val eventTitle = eventDetailsDialogView.findViewbyId(R.id.event_title) as TextView
//                val eventLocation = eventDetailsDialogView.findViewbyId(R.id.event_location) as TextView
//                val eventDescription = eventDetailsDialogView.findViewbyId(R.id.event_description) as TextView

                //val backButton = eventDetailsDialogView.findViewbyId(R.id.back_button) as TextView

                // imports the event details
                // toolbartext.text = organization.name
                alertDialog.setTitle(organization.name)
                eventDetailsDialogView.doors_open_date_time.text = event?.startDate?.substringBefore('T')?.substringAfter('-')
                eventDetailsDialogView.doors_close_date_time.text = event?.startDate?.substringAfter('T')?.substringBefore('.')?.substringBeforeLast(':')
                if (event?.name != "") eventDetailsDialogView.event_title.text = event?.name
                if (event?.location != "") eventDetailsDialogView.event_location.text = event?.location
                if (event?.description != "") eventDetailsDialogView.event_description.text = event?.description

                // dismiss dialog when back button is clicked
                alertDialog.back_button.setOnClickListener {
                    alertDialog.dismiss()
                }
            }

        })

        return rowView
    }

}