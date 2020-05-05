package com.example.digitalpassbook2.student.eventbook

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
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

        val orgName = rowView.findViewById(R.id.club_name) as TextView
        orgName.text = MainActivity.organizationNames[event!!.orgId]

        val orgLogo = rowView.findViewById(R.id.club_logo) as ImageView
        orgLogo.setImageResource(rowView.resources.getIdentifier(
            MainActivity.organizationLogos[event.orgId], "drawable", context.packageName))

        val passDate = rowView.findViewById<TextView>(R.id.event_date)

        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        val dateformatter = SimpleDateFormat("M/d")
        val date = formatter.parse(event.startDate)
        val formattedDate = dateformatter.format(date)

        passDate.text = formattedDate
        val eventId = getItemId(position)

        val sendPass = rowView.findViewById<ImageButton>(R.id.send_pass_button)
        val bounce = rowView.findViewById<ImageButton>(R.id.security_button)

        studentEventListViewModel = StudentEventListViewModel()
        studentEventListViewModel.getStudent(MyUser.id)
        studentEventListViewModel.student.observe(context as FragmentActivity, Observer {
            val student = (it ?: return@Observer)
            if (isBouncer(student, eventId.toInt())) {
                sendPass.visibility=View.INVISIBLE
                bounce.visibility=View.VISIBLE
            }
            else {
                sendPass.visibility=View.VISIBLE
                bounce.visibility=View.INVISIBLE
            }
        })

        sendPass.setOnClickListener {
            val studentEventListViewModel = StudentEventListViewModel()
            studentEventListViewModel.getPassNumber(eventId, MyUser.id)
            studentEventListViewModel.passes.observe(context as FragmentActivity, Observer { it1 ->
                val passes = (it1 ?: return@Observer)
                if (passes.isNotEmpty()) {
                    showDialog(context, passes, rowView)
                }
                else {
                    val noPasses: AlertDialog.Builder? =
                        getActivity(context)?.let { it2 -> AlertDialog.Builder(it2) }
                    noPasses?.setTitle("You have no spots")
                    noPasses?.setNegativeButton("OK") { dialog, _ -> dialog.cancel() }
                    noPasses?.show()
                }
            })
        }

        bounce.setOnClickListener {
            val action = EventbookFragmentDirections.actionNavigationEventbookToNavigationScanPass(eventId)
            rowView.findNavController().navigate(action)
        }

        return rowView
    }

    @SuppressLint("RestrictedApi")
    fun showDialog(context: Context, passes : List<Pass?>, rowView : View) {
        val dialog = Dialog(context)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_event_listview)
        dialog.window?.setLayout(900, 800)
        dialog.window?.setGravity(Gravity.CENTER)
        val btndialog: Button = dialog.findViewById(R.id.btndialog) as Button
        btndialog.setOnClickListener() { dialog.dismiss() }
        val listView: ListView = dialog.findViewById(R.id.listview) as ListView
        val passListAdapter = getActivity(context)?.let { it2 ->
            StudentPassListAdapter(it2, passes as MutableList<Pass?>)
        }
        listView.adapter = passListAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            dialog.dismiss()
            val pass = passes[position]
            val passId = pass?.id
            val orgId = pass?.orgId
            val orgLogoArg = MainActivity.organizationLogos[orgId!!]
            val orgNameArg = MainActivity.organizationNames[orgId]
            val builderInner: AlertDialog.Builder? = getActivity(context)?.let { it -> AlertDialog.Builder(it) }
            builderInner?.setTitle("Choose an action:")
            builderInner?.setPositiveButton("Display") { dialog2, _ ->
                dialog2.dismiss()
                val action = passId?.toLong()?.let { it1 ->
                    EventbookFragmentDirections.actionNavigationEventbookToNavigationDisplayPass(it1, orgId, orgLogoArg, orgNameArg)
                }
                if (action != null) {
                    rowView.findNavController().navigate(action)
                }
            }
            if (!pass.isLocked) {
                builderInner?.setNegativeButton("Send") { dialog2, _ ->
                    dialog2.dismiss()
                    val action = passId?.toLong()?.let { it1 ->
                        EventbookFragmentDirections.actionNavigationEventbookToNavigationSendPass(
                            it1
                        )
                    }
                    if (action != null) {
                        rowView.findNavController().navigate(action)
                    }
                }
            }
            builderInner?.setNeutralButton("Cancel") { dialog2, _ ->
                dialog2.dismiss()
            }
            builderInner?.show()
        }
        dialog.show()
    }

    private fun isBouncer(student: Student, eventsid: Int) : Boolean {
        var isBouncer = false
        if (student.bouncingEvents != null) {
            isBouncer = eventsid in student.bouncingEvents!!
        }
        return isBouncer
    }

}