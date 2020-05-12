package com.example.digitalpassbook2.student.passbook

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.digitalpassbook2.MainActivity
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.*
import com.example.digitalpassbook2.student.eventbook.EventbookFragmentDirections


class StudentPassListAdapter (private val context: Context,
                              private val studentPassList: MutableList<Pass?>,
                              private val dialog: Dialog, private val eventBookView: View) : BaseAdapter() {

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
        val rowView = inflater.inflate(R.layout.adapter_student_pass_list, parent, false)

        val pass = getItem(position)
        val orgId = pass?.orgId
        val orgLogoArg = MainActivity.organizationLogos[orgId!!]
        val orgNameArg = MainActivity.organizationNames[orgId]


        val orgLogo = rowView.findViewById(R.id.club_logo) as ImageView
        orgLogo.setImageResource(rowView.resources.getIdentifier(
            MainActivity.organizationLogos[pass.orgId], "drawable", context.packageName))

        val orgName = rowView.findViewById(R.id.pass_number) as TextView

        val passNumber = position + 1
        orgName.text = "Pass $passNumber"

        val send = rowView.findViewById<ImageView>(R.id.send_pass_button)
        if (pass.isLocked) {
            send.setImageResource(R.drawable.ic_locked)
        }
        else {
            send.setImageResource(R.drawable.ic_send_24dp)
            send.setOnClickListener {
                val action = pass.id.toLong().let { it1 ->
                    EventbookFragmentDirections.actionNavigationEventbookToNavigationSendPass(
                        it1
                    )
                }
                eventBookView.findNavController().navigate(action)
                dialog.dismiss()
            }
        }

        rowView.setOnClickListener {
            val action = pass.let { it1 ->
                EventbookFragmentDirections.actionNavigationEventbookToNavigationDisplayPass(
                    it1,
                    orgId,
                    orgLogoArg,
                    orgNameArg
                )
            }
            eventBookView.findNavController().navigate(action)
            dialog.dismiss()
        }

        return rowView
    }

}
