package com.example.lib

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ListView

class XListView(context: Context?, attrs: AttributeSet?) : ListView(context, attrs), Scrollable {

	override fun setScrollEnabled(isEnabled: Boolean) {
		mIsScrollEnabled = isEnabled
	}

	var mIsScrollEnabled = true

	override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
		val result = super.onInterceptTouchEvent(ev)
		return if (mIsScrollEnabled) {
			result
		} else {
			false
		}
	}

}