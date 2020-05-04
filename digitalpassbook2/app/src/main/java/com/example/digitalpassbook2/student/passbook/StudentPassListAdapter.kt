package com.example.digitalpassbook2.student.passbook

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import com.example.digitalpassbook2.MainActivity
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.*
import com.example.digitalpassbook2.student.eventbook.EventbookFragmentDirections


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
        val rowView = inflater.inflate(R.layout.adapter_student_pass_list, parent, false)

        val pass = getItem(position)

        val orgLogo = rowView.findViewById(R.id.club_logo) as ImageView
        orgLogo.setImageResource(rowView.resources.getIdentifier(
            MainActivity.organizationLogos[pass?.orgId!!], "drawable", context.packageName))

        val orgName = rowView.findViewById(R.id.club_name) as TextView
        orgName.text = MainActivity.organizationNames[pass.orgId]

        val orgId = pass.orgId
        val orgLogoArg = MainActivity.organizationLogos[pass.orgId]
        val orgNameArg = MainActivity.organizationNames[pass.orgId]
        val passId = getItemId(position)

//        rowView.findViewById<RelativeLayout>(R.id.pass_row).setOnClickListener {
//            val action =
//                EventbookFragmentDirections.
//                    actionNavigationEventbookToNavigationDisplayPass(passId, orgId, orgLogoArg, orgNameArg)
//            val navController = Navigation.findNavController(
//                context as FragmentActivity,
//                R.id.student_nav_host_fragment
//            )
//            navController.navigate(action)
//        }
//
//        rowView.findViewById<ImageButton>(R.id.send_pass_button).setOnClickListener {
//            val action =
//                EventbookFragmentDirections.actionNavigationEventbookToNavigationSendPass(passId)
//            val navController = Navigation.findNavController(
//                context as FragmentActivity,
//                R.id.student_nav_host_fragment
//            )
//            navController.navigate(action)
//        }

        return rowView
    }

}
