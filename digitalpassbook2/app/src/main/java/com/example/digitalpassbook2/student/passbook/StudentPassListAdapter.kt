package com.example.digitalpassbook2.student.passbook

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.findNavController
import com.example.digitalpassbook2.MainActivity
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.*


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
        val rowView = inflater.inflate(R.layout.adapter_pass_list, parent, false)

        val pass = getItem(position)

        val orgName = rowView.findViewById(R.id.club_name) as TextView
        orgName.text = MainActivity.organizationNames[pass!!.orgId]

        val orgLogo = rowView.findViewById(R.id.club_logo) as ImageView
        orgLogo.setImageResource(rowView.resources.getIdentifier(
            MainActivity.organizationLogos[pass.orgId], "drawable", context.packageName))

        val passDate = rowView.findViewById<TextView>(R.id.pass_date)
        passDate.text = pass.date.substring(5,10)

        val orgId = pass.orgId
        val orgLogoArg = MainActivity.organizationLogos[pass.orgId]
        val orgNameArg = MainActivity.organizationNames[pass.orgId]
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

        return rowView
    }

}
