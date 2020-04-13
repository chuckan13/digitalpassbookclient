package com.example.digitalpassbook2.student.passbook

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
import com.example.digitalpassbook2.server.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StudentPassListAdapter (private val context: Context,
                              private val studentPassList: MutableList<Pass?>) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val organizationServe by lazy {
        OrganizationService.create()
    }

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
        val rowView = inflater.inflate(R.layout.adapter_pass_list, parent, false)
        val pass = getItem(position)
        val passId = getItemId(position)

        val clubName = rowView.findViewById(R.id.club_name) as TextView
        val clubLogo = rowView.findViewById(R.id.club_logo) as ImageView
        val organizationCall = pass?.orgId?.let { organizationServe[it] }
        organizationCall?.enqueue(object : Callback<Organization?> {
            override fun onResponse(call: Call<Organization?>?, response: Response<Organization?>?) {
                val club = response?.body()
                val clubId = club?.id
                clubName.text = club?.name
                clubLogo.setImageResource(rowView.resources.getIdentifier(club?.logo, "drawable", context.packageName))
    
                rowView.findViewById<Button>(R.id.view_button).setOnClickListener {
                    val action =
                        PassbookFragmentDirections.actionNavigationPassbookToNavigationDisplayPass(passId, clubId!!)
                    rowView.findNavController().navigate(action)
                }

                rowView.findViewById<Button>(R.id.send_button).setOnClickListener {
                    val action =
                        PassbookFragmentDirections.actionNavigationPassbookToNavigationSendPass(passId)
                    rowView.findNavController().navigate(action)
                }
            }
            override fun onFailure(call: Call<Organization?>?, t: Throwable?) {
                println("failure")
            }
        })

        return rowView
    }

}
