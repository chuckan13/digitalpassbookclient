package com.example.digitalpassbook2.student.passbook

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
import androidx.navigation.findNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.*


class StudentPassListAdapter (private val context: Context,
                              private val studentPassList: MutableList<Pass?>) : BaseAdapter() {

    private lateinit var studentPassListViewModel: StudentPassListViewModel

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

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        studentPassListViewModel = ViewModelProviders.of(context as FragmentActivity).get(
            StudentPassListViewModel::class.java)
        val rowView = inflater.inflate(R.layout.adapter_pass_list, parent, false)

        val pass = getItem(position)

        var organization : Organization? = null
        studentPassListViewModel.organization.observe(context, Observer {
            pass?.orgId?.let { it1 -> studentPassListViewModel.getOrganization(it1) }
            organization = it ?: return@Observer
        })

        val orgName = rowView.findViewById(R.id.club_name) as TextView
        orgName.text = organization?.name

        val orgLogo = rowView.findViewById(R.id.club_logo) as ImageView
        orgLogo.setImageResource(rowView.resources.getIdentifier(organization?.logo, "drawable", context.packageName))

        val orgId = organization?.id
        val passId = getItemId(position)

        rowView.findViewById<Button>(R.id.view_button).setOnClickListener {
            val action =
                PassbookFragmentDirections.actionNavigationPassbookToNavigationDisplayPass(passId, orgId!!)
            rowView.findNavController().navigate(action)
        }

        rowView.findViewById<Button>(R.id.send_button).setOnClickListener {
            val action =
                PassbookFragmentDirections.actionNavigationPassbookToNavigationSendPass(passId)
            rowView.findNavController().navigate(action)
        }

        return rowView
    }

}
