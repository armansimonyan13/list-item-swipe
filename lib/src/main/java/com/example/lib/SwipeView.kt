package com.example.lib

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.GestureDetectorCompat
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import kotlin.math.abs

class SwipeView(context: Context?, @Suppress("UNUSED_PARAMETER") attributeSet: AttributeSet) : FrameLayout(context) {

	private var x = 0

	private var gestureDetector: GestureDetectorCompat = GestureDetectorCompat(context, MyGestureDetector())

	lateinit var mainView: View
	lateinit var guideline: View

	private var animator: ValueAnimator? = null

	override fun onFinishInflate() {
		super.onFinishInflate()
		mainView = findViewById(R.id.swipe_main)
		guideline = findViewById(R.id.swipe_guideline)
	}

	@SuppressLint("ClickableViewAccessibility")
	override fun onTouchEvent(event: MotionEvent): Boolean {
		gestureDetector.onTouchEvent(event)
		when (event.actionMasked) {
			MotionEvent.ACTION_DOWN -> {

			}
			MotionEvent.ACTION_UP -> {
				postDelayed({
					if (animator?.isStarted == false) {
						adjust()
					}
				}, 50)
			}
		}
		return true //super.onTouchEvent(event)
	}

	inner class MyGestureDetector : GestureDetector.SimpleOnGestureListener() {

		override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
			x -= distanceX.toInt()

			if (x > 0) {
				x = 0
			}
			val threshold = width - guideline.right
			if (abs(x) > (threshold)) {
				x = -threshold
			}
			log("threshold: $threshold")
			log("x: $x")

			val layoutParams = mainView.layoutParams as MarginLayoutParams
			layoutParams.leftMargin = x
			layoutParams.rightMargin = -x
			mainView.layoutParams = layoutParams

			return true
		}

		override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
			log("velocityX: $velocityX")

//			val maxVelocity = ViewConfiguration.get(context).scaledMaximumFlingVelocity

			if (abs(velocityX) > 2000) {
				open()
			} else {
				adjust()
			}
			return false
		}

	}

	fun close() {
		val lp = mainView.layoutParams as MarginLayoutParams
		val startValue = lp.leftMargin
		if (startValue == 0) {
			return
		}
		val valueAnimator = ValueAnimator.ofInt(startValue, 0)
		valueAnimator.addUpdateListener {
			val layoutParams = mainView.layoutParams as MarginLayoutParams
			val value = it.animatedValue as Int
			layoutParams.leftMargin = value
			layoutParams.rightMargin = -value
			mainView.layoutParams = layoutParams
		}
		valueAnimator.addListener(object : AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator?) {
				x = 0
			}
		})
		valueAnimator.start()

		animator?.end()
		animator = valueAnimator
	}

	fun open() {
		log("open")
		val lp = mainView.layoutParams as MarginLayoutParams
		val startValue = lp.rightMargin
		val endValue = width - guideline.right
		val valueAnimator = ValueAnimator.ofInt(startValue, endValue)
		valueAnimator.addUpdateListener {
			val lp = mainView.layoutParams as MarginLayoutParams
			val value = it.animatedValue as Int
			lp.leftMargin = -value
			lp.rightMargin = value
			mainView.layoutParams = lp
		}
		valueAnimator.addListener(object : AnimatorListenerAdapter() {
			override fun onAnimationEnd(animation: Animator?) {
				x = endValue
			}
		})
		valueAnimator.start()

		animator?.end()
		animator = valueAnimator
	}

	fun reset() {
		val lp = mainView.layoutParams as MarginLayoutParams
		lp.leftMargin = 0
		lp.rightMargin = 0
		mainView.layoutParams = lp

		animator?.end()
	}

	private fun adjust() {
		log("adjust")
		val lp = mainView.layoutParams as MarginLayoutParams
		val margin = lp.rightMargin
		val path = width - guideline.right
		if (margin > path / 2) {
			open()
		} else {
			close()
		}
	}

	private fun log(message: String) {
		Log.d("SwipeView", message)
	}

}