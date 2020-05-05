package com.example.digitalpassbook2.student.eventbook

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.digitalpassbook2.MainActivity
import com.example.digitalpassbook2.MyUser
import com.example.digitalpassbook2.R
import com.example.digitalpassbook2.server.Event
import com.example.digitalpassbook2.server.Pass
import com.example.digitalpassbook2.server.Student
import com.example.digitalpassbook2.student.passbook.StudentPassListAdapter
import com.google.android.material.internal.ContextUtils.getActivity
import java.text.SimpleDateFormat


class StudentEventListAdapter (private val context: Context,
                               private val studentEventList: MutableList<Event?>) : BaseAdapter() {

    private lateinit var studentEventListViewModel: StudentEventListViewModel

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

    @SuppressLint("ViewHolder", "RestrictedApi")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.adapter_student_event_list, parent, false)

        val event = getItem(position)
        val eventId = getItemId(position)

        val orgName = rowView.findViewById(R.id.club_name) as TextView
        orgName.text = MainActivity.organizationNames[event!!.orgId]

        val orgLogo = rowView.findViewById(R.id.club_logo) as ImageView
        orgLogo.setImageResource(rowView.resources.getIdentifier(
            MainActivity.organizationLogos[event.orgId], "drawable", context.packageName))

        ////format and set the date
        val passDate = rowView.findViewById<TextView>(R.id.event_date)
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val dateformatter = SimpleDateFormat("MM/dd")
        val date = formatter.parse(event.startDate)
        val formattedDate = dateformatter.format(date)
        passDate.text = formattedDate


        studentEventListViewModel = StudentEventListViewModel()
        studentEventListViewModel.getStudent(MyUser.id)

        val bounce = rowView.findViewById<ImageButton>(R.id.security_button)
        studentEventListViewModel.student.observe(context as FragmentActivity, Observer {
            val student = (it ?: return@Observer)
            if (isBouncer(student, eventId.toInt())) {
                bounce.visibility=View.VISIBLE
            }
        })

        studentEventListViewModel.passes.observe(context, Observer { it1 ->
            val passes = (it1 ?: return@Observer)
            if (passes.isNotEmpty()) {
                showDialog(context, passes, rowView)
            }
            else {
                val noPasses: AlertDialog.Builder? =
                    getActivity(context)?.let { it2 -> AlertDialog.Builder(it2) }
                noPasses?.setTitle("You Have No Spots For This Event")
                noPasses?.setNeutralButton("OK", null)
                Log.d("StudentEventListAdapter", "Neutral Message")
                noPasses?.show()
            }
        })

        val eventRow = rowView.findViewById<RelativeLayout>(R.id.event_row)
        eventRow.setOnClickListener {
            studentEventListViewModel.getPassNumber(eventId, MyUser.id)
        }

        bounce.setOnClickListener {
            val action = EventbookFragmentDirections.actionNavigationEventbookToNavigationScanPass(eventId)
            rowView.findNavController().navigate(action)
        }

        return rowView
    }

    @SuppressLint("RestrictedApi")
    fun showDialog(context: Context, passes : List<Pass?>, rowView : View) {
        // create dialog to display the passes
        val dialog = Dialog(context)
        dialog.setCancelable(true)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(R.layout.dialog_event_listview)
        dialog.window?.setLayout(900, 800)
        dialog.window?.setGravity(Gravity.CENTER)
        val btndialog: Button = dialog.findViewById(R.id.btndialog) as Button
        btndialog.setOnClickListener { dialog.dismiss() }

        // set up dialog to contain a list of passes
        val passListAdapter = getActivity(context)?.let { it2 ->
            StudentPassListAdapter(it2, passes as MutableList<Pass?>, dialog, rowView)
        }

        val passListView: ListView = dialog.findViewById(R.id.listview) as ListView
        passListView.adapter = passListAdapter
        passListView.visibility = View.VISIBLE

        dialog.show()
    }

    private fun isBouncer(student: Student, eventsid: Int): Boolean {
        var isBouncer = false
        if (student.bouncingEvents != null) {
            isBouncer = eventsid in student.bouncingEvents!!
        }
        return isBouncer
    }
}
