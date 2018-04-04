package com.example.armansimonyan.swipelistview.list_view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.armansimonyan.swipelistview.R
import com.example.lib.XManager
import com.example.lib.XListView

class ListViewAdapter(listView: XListView) : BaseAdapter() {

	private val xManager = XManager()

	init {
		xManager.setViewGroup(listView)
	}

	private val itemList = mutableListOf<String>()

	init {
		(0..20).forEach {
			itemList.add("String $it")
		}
	}

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
		val view = if (convertView != null) {
			xManager.resetItemView(convertView)
			convertView
		} else {
			val v = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)
			xManager.initItemView(v)
			val viewHolder = ViewHolder()
			viewHolder.textView = v.findViewById(R.id.text)
			v.tag = viewHolder
			v
		}

		val viewHolder = view.tag as ViewHolder
		viewHolder.textView.text = getItem(position)

		return view
	}

	override fun getItem(position: Int): String {
		return itemList[position]
	}

	override fun getItemId(position: Int): Long {
		return position.toLong()
	}

	override fun getCount(): Int {
		return itemList.size
	}

	fun log(message: String) {
		Log.d("ListViewActivity", message)
	}

	class ViewHolder {

		lateinit var textView: TextView

	}
}


