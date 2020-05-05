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

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.adapter_student_pass_list, parent, false)

        val pass = getItem(position)

        val orgLogo = rowView.findViewById(R.id.club_logo) as ImageView
        orgLogo.setImageResource(rowView.resources.getIdentifier(
            MainActivity.organizationLogos[pass?.orgId!!], "drawable", context.packageName))

        val orgName = rowView.findViewById(R.id.club_name) as TextView
        orgName.text = "Pass: $position"

        val access = rowView.findViewById(R.id.access) as ImageView
        if (pass.isLocked) {
            access.setImageResource(R.drawable.ic_locked)
        }
        else {
            access.setImageResource(R.drawable.ic_edit)
        }

        return rowView
    }

}
