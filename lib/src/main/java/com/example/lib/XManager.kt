package com.example.lib

import android.annotation.SuppressLint
import android.support.v4.view.GestureDetectorCompat
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import kotlin.math.abs

class XManager {

	private var viewGroup: ViewGroup? = null

	private var isItemScrollStarted = false
	private var isListScrollStarted = false

	private var listGestureDetector: GestureDetectorCompat? = null
	private var listItemGestureDetector: GestureDetectorCompat? = null

	@SuppressLint("ClickableViewAccessibility")
	fun setViewGroup(viewGroup: ViewGroup) {
		this.viewGroup = viewGroup

		listGestureDetector = GestureDetectorCompat(
				viewGroup.context,
				object : GestureDetector.SimpleOnGestureListener() {

					override fun onDown(e: MotionEvent?): Boolean {
						isListScrollStarted = false
						isItemScrollStarted = false

						if (viewGroup is Scrollable) {
							viewGroup.setScrollEnabled(true)
						}
						return false
					}

					override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
						log("listGestureDetector: onScroll: distanceX: $distanceX, distanceY: $distanceY")

						info()

						if (isListScrollStarted || isItemScrollStarted) {
							return false
						}

						if (abs(distanceX) > abs(distanceY)) {
							isItemScrollStarted = true

							if (viewGroup is Scrollable) {
								viewGroup.setScrollEnabled(false)
							}
						} else {
							isListScrollStarted = true

							if (viewGroup is Scrollable) {
								viewGroup.setScrollEnabled(true)
							}
						}

						closeAll()

						return false
					}

				}
		)

		listItemGestureDetector = GestureDetectorCompat(
				viewGroup.context,
				object : GestureDetector.SimpleOnGestureListener() {

					override fun onDown(e: MotionEvent?): Boolean {
						isListScrollStarted = false
						isItemScrollStarted = false

						if (viewGroup is Scrollable) {
							viewGroup.setScrollEnabled(true)
						}
						return false
					}

					override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
						log("listItemGestureDetector: onScroll: distanceX: $distanceX, distanceY: $distanceY")

						info()

						if (isListScrollStarted || isItemScrollStarted) {
							return false
						}

						if (abs(distanceX) > abs(distanceY)) {
							isItemScrollStarted = true

							if (viewGroup is Scrollable) {
								viewGroup.setScrollEnabled(false)
							}
						} else {
							isListScrollStarted = true

							if (viewGroup is Scrollable) {
								viewGroup.setScrollEnabled(true)
							}
						}

						closeAll()

						return false
					}

				}
		)

		viewGroup.setOnTouchListener(View.OnTouchListener { _, event ->
			listGestureDetector?.onTouchEvent(event)
			return@OnTouchListener false
		})
	}

	fun initItemView(view: View) {
		val onTouchListView = View.OnTouchListener { _, event ->
			listItemGestureDetector?.onTouchEvent(event)
			return@OnTouchListener false
		}
		view.setOnTouchListener(onTouchListView)
	}

	fun resetItemView(view: View) {
		if (view is SwipeView) {
			view.reset()
		}
	}

	fun closeAll() {
		viewGroup?.let { viewGroup ->
			(0 until viewGroup.childCount).forEach {
				val child = viewGroup.getChildAt(it)
				if (child is SwipeView) {
					child.close()
				}
			}
		}
	}

	fun info() {
		log("isListScrollStarted: $isListScrollStarted")
		log("isItemScrollStarted: $isItemScrollStarted")
	}

	fun log(message: String) {
		Log.d("XManager", message)
	}

}
