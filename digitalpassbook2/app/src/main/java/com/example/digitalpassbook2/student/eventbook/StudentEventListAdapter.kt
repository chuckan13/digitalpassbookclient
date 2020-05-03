package com.example.digitalpassbook2.student.eventbook

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.digitalpassbook2.MainActivity
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

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.adapter_pass_list, parent, false)

        val event = getItem(position)

        val orgName = rowView.findViewById(R.id.club_name) as TextView
        orgName.text = MainActivity.organizationNames[event!!.orgId]

        val orgLogo = rowView.findViewById(R.id.club_logo) as ImageView
        orgLogo.setImageResource(rowView.resources.getIdentifier(
            MainActivity.organizationLogos[event.orgId], "drawable", context.packageName))

        val passDate = rowView.findViewById<TextView>(R.id.pass_date)
        passDate.text = event.startDate.substring(5,10)

        val orgId = event.orgId
        val orgLogoArg = MainActivity.organizationLogos[event.orgId]
        val orgNameArg = MainActivity.organizationNames[event.orgId]
        val eventId = getItemId(position)

        rowView.setOnClickListener {

        }

        return rowView
    }

}