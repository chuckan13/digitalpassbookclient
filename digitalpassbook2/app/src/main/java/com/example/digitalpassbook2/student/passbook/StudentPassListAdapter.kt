package com.example.digitalpassbook2.student.passbook

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.*
import org.w3c.dom.Text
import java.time.format.DateTimeFormatter


class StudentPassListAdapter (private val context: Context,
                              private val studentPassList: MutableList<Pass?>) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return studentPassList.size
    }

    override fun getItem(position: Int): Pass? {
        return studentPassList[position]
    }

    override fun getItemId(position: Int): Long {
        return studentPassList[position]?.id?.toLong()!!
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val studentPassListViewModel = StudentPassListViewModel()
        val rowView = inflater.inflate(R.layout.adapter_pass_list, parent, false)

        val pass = getItem(position)
        pass?.orgId?.let { it1 -> studentPassListViewModel.getOrganization(it1) }
        studentPassListViewModel.organization.observe(context as FragmentActivity, Observer {
            val organization = it ?: return@Observer

            val orgName = rowView.findViewById(R.id.club_name) as TextView
            orgName.text = organization.name

            val orgLogo = rowView.findViewById(R.id.club_logo) as ImageView
            orgLogo.setImageResource(rowView.resources.getIdentifier(organization.logo, "drawable", context.packageName))

            val passDate = rowView.findViewById<TextView>(R.id.pass_date)
            passDate.text = pass?.date?.substring(5,10)

            val orgId = organization.id
            val orgLogoArg = organization.logo
            val orgNameArg = organization.name
            val passId = getItemId(position)

            rowView.setOnClickListener {
                val action =
                    PassbookFragmentDirections.
                        actionNavigationPassbookToNavigationDisplayPass(passId, orgId, orgLogoArg, orgNameArg)
                rowView.findNavController().navigate(action)
            }

            rowView.findViewById<ImageButton>(R.id.send_button).setOnClickListener {
                val action =
                    PassbookFragmentDirections.actionNavigationPassbookToNavigationSendPass(passId)
                rowView.findNavController().navigate(action)
            }
        })

        return rowView
    }

}
