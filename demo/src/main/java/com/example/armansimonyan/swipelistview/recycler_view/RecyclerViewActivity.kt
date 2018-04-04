package com.example.armansimonyan.swipelistview.recycler_view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.armansimonyan.swipelistview.R
import com.example.lib.XRecyclerView

class RecyclerViewActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		setContentView(R.layout.activity_recycler_view)

		val recyclerView: XRecyclerView = findViewById(R.id.recycler)

		recyclerView.layoutManager = LinearLayoutManager(
				this@RecyclerViewActivity,
				RecyclerView.VERTICAL,
				false
		)

		recyclerView.adapter = RecyclerViewAdapter(recyclerView)
	}

}