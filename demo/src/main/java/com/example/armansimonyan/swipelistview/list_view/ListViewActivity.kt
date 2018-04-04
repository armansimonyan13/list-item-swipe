package com.example.armansimonyan.swipelistview.list_view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.armansimonyan.swipelistview.R
import com.example.lib.XListView

class ListViewActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_list_view)

		val listView: XListView = findViewById(R.id.list)

		val adapter = ListViewAdapter(listView)

		listView.adapter = adapter
	}

}
