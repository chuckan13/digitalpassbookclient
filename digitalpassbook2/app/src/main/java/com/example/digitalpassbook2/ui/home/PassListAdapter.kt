package com.example.digitalpassbook2.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.digitalpassbook2.Pass
import com.example.digitalpassbook2.R


class PassListAdapter (private val context: Context,
                       private val passList: MutableList<Pass?>) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return passList.size
    }

    override fun getItem(position: Int): Pass? {
        return passList[position]
    }

    override fun getItemId(position: Int): Long {
        return passList[position]?.id?.toLong()!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.adapter_pass_list, parent, false)

        val passName = rowView.findViewById(R.id.pass_list) as TextView

        val pass = getItem(position)
        val passId = getItemId(position)
        passName.text = pass?.passName

        rowView.findViewById<Button>(R.id.view_button).setOnClickListener {
            val action =
                HomeFragmentDirections.actionNavigationHomeToNavigationDisplayPass(passId)
            rowView.findNavController().navigate(action)
        }

        rowView.findViewById<Button>(R.id.send_button).setOnClickListener {
            val action =
                HomeFragmentDirections.actionNavigationHomeToNavigationSendPass(passId)
            rowView.findNavController().navigate(action)
        }

        return rowView
    }

}
