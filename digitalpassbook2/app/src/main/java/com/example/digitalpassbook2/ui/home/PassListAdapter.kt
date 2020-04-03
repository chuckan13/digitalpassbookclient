package com.example.digitalpassbook2.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.digitalpassbook2.Pass
import com.example.digitalpassbook2.R
import kotlinx.android.synthetic.main.pass_list.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PassListAdapter (private val context: Context,
                       private val passList: MutableList<Pass?>) : BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return passList.size
    }

    override fun getItem(position: Int): String? {
        return passList[position]?.passName
    }

    override fun getItemId(position: Int): Long {
        return passList[position]?.id?.toLong()!!
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = inflater.inflate(R.layout.pass_list, parent, false)

        val passName = rowView.findViewById(R.id.pass_list) as TextView
        val viewButton = rowView.findViewById(R.id.view_button) as Button
        val sendButton = rowView.findViewById(R.id.send_button) as Button

        passName.text = getItem(position)

        sendButton.setOnClickListener {

        }
        viewButton.setOnClickListener {

        }

        return rowView
    }

}