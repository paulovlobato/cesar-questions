package com.victordevelopment.questao4

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SearchListAdapter(var list: List<String>?, private val activity: Activity) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = activity.layoutInflater.inflate(R.layout.list_line, viewGroup, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }

        viewHolder.tvWord?.text = list?.get(position)

        return view;

    }

    override fun getItem(idx: Int): Any? {
        Log.i("POSITION", idx.toString())
        if (list != null) {
            return list!!.get(idx)
        }
        return null
    }

    override fun getItemId(idx: Int): Long {
        return idx.toLong();
    }

    override fun getCount(): Int {
        if (list != null) {
            return list!!.size
        }
        return 0
    }

    private class ViewHolder(row: View) {
        var tvWord: TextView? = null

        init {
            this.tvWord = row.findViewById(R.id.tvWord)
        }
    }
}