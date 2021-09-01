package com.reactnativepagerview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import kotlin.math.absoluteValue
import kotlin.math.sign

/**
 * Layout to wrap a scrollable component inside a ViewPager. Provided as a solution to the problem
 * where pages of ViewPager have nested scrollable elements that scroll in the same direction as
 * ViewPager. The scrollable element needs to be the immediate and only child of this host layout.
 *
 * This solution has limitations when using multiple levels of nested scrollable elements
 * (e.g. a horizontal RecyclerView in a vertical RecyclerView in a horizontal ViewPager).
 */
class NestedScrollableHost : FrameLayout {
  constructor(context: Context) : super(context)
  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

  private var touchSlop = 0
  private var initialX = 0f
  private var initialY = 0f
  private val parentViewPager: ViewPager?
    get() {
      var v: View? = parent as? View
      while (v != null && v !is ViewPager) {
        v = v.parent as? View
      }
      return v as? ViewPager
    }

  private val child: View? get() = if (childCount > 0) getChildAt(0) else null

  init {
    touchSlop = ViewConfiguration.get(context).scaledTouchSlop
  }

  private fun canChildScroll(orientation: Int, delta: Float): Boolean {
    val direction = -delta.sign.toInt()
    return when (orientation) {
      0 -> child?.canScrollHorizontally(direction) ?: false
      1 -> child?.canScrollVertically(direction) ?: false
      else -> throw IllegalArgumentException()
    }
  }

  override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
    handleInterceptTouchEvent(e)
    return super.onInterceptTouchEvent(e)
  }

  private fun handleInterceptTouchEvent(e: MotionEvent) {
    val orientation = 0;

    // Early return if child can't scroll in same direction as parent
    if (!canChildScroll(orientation, -1f) && !canChildScroll(orientation, 1f)) {
      return
    }

    if (e.action == MotionEvent.ACTION_DOWN) {
      initialX = e.x
      initialY = e.y
      parent.requestDisallowInterceptTouchEvent(true)
    } else if (e.action == MotionEvent.ACTION_MOVE) {
      val dx = e.x - initialX
      val dy = e.y - initialY
      val isVpHorizontal = orientation == 0

      // assuming ViewPager touch-slop is 2x touch-slop of child
      val scaledDx = dx.absoluteValue * if (isVpHorizontal) .5f else 1f
      val scaledDy = dy.absoluteValue * if (isVpHorizontal) 1f else .5f

      parent.requestDisallowInterceptTouchEvent(true)
    }
  }
}