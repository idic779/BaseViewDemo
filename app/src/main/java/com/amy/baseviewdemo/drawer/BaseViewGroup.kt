package com.amy.baseviewdemo.drawer

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop

/**
 * 大幅度提升绘制的自定义ViewGroup
 */
abstract class BaseViewGroup(context: Context) : ViewGroup(context) {
    protected fun View.defaultWidthMeasureSpec(parentView: ViewGroup): Int {
        return when (layoutParams.width) {
            ViewGroup.LayoutParams.MATCH_PARENT -> parentView.measuredWidth.toExactlyMeasureSpec()
            ViewGroup.LayoutParams.WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT.toAtMostMeasureSpec()
            0 -> throw IllegalAccessException("Need special treatment for $this")
            else -> layoutParams.width.toExactlyMeasureSpec()
        }
    }

    protected fun View.defaultHeightMeasureSpec(parentView: ViewGroup): Int {
        return when (layoutParams.height) {
            ViewGroup.LayoutParams.MATCH_PARENT -> parentView.measuredHeight.toExactlyMeasureSpec()
            ViewGroup.LayoutParams.WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT.toAtMostMeasureSpec()
            0 -> throw IllegalAccessException("Need special treatment for $this")
            else -> layoutParams.height.toExactlyMeasureSpec()
        }
    }

    protected fun Int.toExactlyMeasureSpec(): Int {
        return View.MeasureSpec.makeMeasureSpec(this, View.MeasureSpec.EXACTLY)
    }

    protected fun Int.toAtMostMeasureSpec(): Int {
        return View.MeasureSpec.makeMeasureSpec(this, View.MeasureSpec.AT_MOST)
    }

    protected fun View.autoMeasure() {
        measure(
                this.defaultWidthMeasureSpec(parentView = this@BaseViewGroup),
                this.defaultHeightMeasureSpec(parentView = this@BaseViewGroup)
        )
    }

    protected fun View.toHorizontalCenter(parentView: ViewGroup): Int {
        return (parentView.measuredWidth - measuredWidth) / 2
    }

    protected fun View.toVerticalCenter(parentView: ViewGroup): Int {
        return (parentView.measuredHeight - measuredHeight) / 2
    }

    protected fun View.layout(x: Int, y: Int, fromRight: Boolean = false) {
        if (!fromRight) {
            layout(x, y, x + measuredWidth, y + measuredHeight)
        } else {
            layout(this@BaseViewGroup.measuredWidth - x - measuredWidth, y)
        }
    }

    protected val Int.dp: Int get() = (this * resources.displayMetrics.density + 0.5f).toInt()
    protected val View.measuredWidthWithMargins get() = (measuredWidth + marginLeft + marginRight)
    protected val View.measuredHeightWithMargins get() = (measuredHeight + marginTop + marginBottom)

    protected class LayoutParams(width: Int, height: Int) : ViewGroup.MarginLayoutParams(width, height)
}