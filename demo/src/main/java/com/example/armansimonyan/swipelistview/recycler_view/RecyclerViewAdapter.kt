package com.example.armansimonyan.swipelistview.recycler_view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.armansimonyan.swipelistview.R
import com.example.lib.XManager
import com.example.lib.XRecyclerView

class RecyclerViewAdapter(recyclerView: XRecyclerView) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

	private val xManager = XManager()

	private val itemList = mutableListOf<String>()

	init {
		xManager.setViewGroup(recyclerView)
	}

	init {
		(0..20).forEach {
			itemList.add("String $it")
		}
	}

	override fun getItemCount(): Int {
		return itemList.size
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.view_item, parent, false)
		xManager.initItemView(view)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		xManager.resetItemView(holder.itemView)
		holder.textView.text = itemList[position]
	}

	class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		val textView: TextView = itemView.findViewById(R.id.text)
	}

}
