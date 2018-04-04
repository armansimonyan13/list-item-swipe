package com.example.lib

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent

class XRecyclerView(context: Context?, attrs: AttributeSet?) : RecyclerView(context, attrs), Scrollable {

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
