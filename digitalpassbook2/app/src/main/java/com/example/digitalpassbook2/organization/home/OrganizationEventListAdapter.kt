package com.example.digitalpassbook2.organization.home

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.MyUser
import com.example.digitalpassbook2.server.*
import java.text.SimpleDateFormat


class OrganizationEventListAdapter (private val context: Context,
                                    private val eventList: List<Event?>) : BaseAdapter() {

    private lateinit var organizationEventListViewModel: OrganizationEventListViewModel

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

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
        organizationEventListViewModel = OrganizationEventListViewModel()
        val rowView = inflater.inflate(R.layout.adapter_organization_event_list, parent, false)

        val clubLogo = rowView.findViewById(R.id.event_club_logo) as ImageView
        clubLogo.setImageResource(rowView.resources.getIdentifier(MyUser.logo, "drawable", context.packageName))

        val clubName = rowView.findViewById(R.id.event_club_name) as TextView
        clubName.text = MyUser.name

        val eventDate = rowView.findViewById(R.id.event_date) as TextView
        val event = getItem(position)

        // formats and sets the date info
        val passDate = rowView.findViewById<TextView>(R.id.event_date)
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val dateformatter = SimpleDateFormat("M/d")
        val date = formatter.parse(event?.startDate)
        val formattedDate = dateformatter.format(date)
        eventDate.text = formattedDate

        rowView.findViewById<ImageButton>(R.id.details_button).setOnClickListener {
            val action =
                HomeFragmentDirections.actionNavigationHomeToNavigationEventDetails(getItemId(position))
            rowView.findNavController().navigate(action)
        }

        val organizationRow = rowView.findViewById<RelativeLayout>(R.id.organization_row)
        organizationRow.setOnClickListener {
            showDialog(context, event!!)
        }

        return rowView
    }

    @SuppressLint("RestrictedApi")
    fun showDialog(context: Context, event: Event) {
        organizationEventListViewModel = OrganizationEventListViewModel()
        val dialog = Dialog(context)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.dialog_organization_listview)
        dialog.window?.setGravity(Gravity.CENTER)
        dialog.window?.setLayout(600, 470)

        val distributed = dialog.findViewById<TextView>(R.id.passes_distributed)
        event.id.let { it1 -> organizationEventListViewModel.getStudents(it1) }
        organizationEventListViewModel.students.observe(context as FragmentActivity, Observer {
            val students = it ?: return@Observer
            distributed.text = students.size.toString()
        })

        val capacity = dialog.findViewById<TextView>(R.id.event_capacity)
        event.id.let { it1 -> organizationEventListViewModel.getPasses(it1) }
        organizationEventListViewModel.passes.observe(context as FragmentActivity, Observer {
            val passes = it ?: return@Observer
            capacity.text = passes.size.toString()
        })

        val arrived = dialog.findViewById<TextView>(R.id.students_arrived)
        arrived.text = event.numArrived.toString()

        val okDialog: Button = dialog.findViewById(R.id.ok_button) as Button
        okDialog.setOnClickListener() { dialog.dismiss() }

        dialog.show()
    }

}
