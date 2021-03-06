package com.amy.baseviewdemo.drawer

import android.content.Context
import android.util.TypedValue
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import com.amy.baseviewdemo.R

class DemoView(context: Context) : BaseViewGroup(context) {

    init {
        isClickable = true
        isFocusable = true
        clipToPadding = false
    }


    val leftText = AppCompatTextView(context).apply {
        layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).also {
            it.leftMargin = 19.dp
            it.rightMargin = 19.dp
            it.bottomMargin = 10.dp
            it.topMargin = 10.dp
        }
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        setTextColor(ContextCompat.getColor(context, R.color.white))
        setPadding(9.dp, 3.dp, 9.dp, 3.dp)
        background = ContextCompat.getDrawable(context, R.drawable.shape_radius12)
        addView(this)
    }
    val desText = AppCompatTextView(context).apply {
        layoutParams = LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).also {
            it.leftMargin = 3.dp
            it.rightMargin = 3.dp
        }
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
        setTextColor(ContextCompat.getColor(context, R.color.teal_200))
        addView(this)
    }
    val icon = AppCompatImageView(context).apply {
        val iconSize = 40.dp
        layoutParams = LayoutParams(iconSize, iconSize)
        setBackgroundResource(R.mipmap.icon_bear)
        addView(this)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        leftText.autoMeasure()
        desText.autoMeasure()
        icon.autoMeasure()
        val height = paddingTop + paddingBottom +leftText.measuredHeightWithMargins+desText.measuredHeight+icon.measuredHeight
        val width = desText.measuredWidthWithMargins.coerceAtLeast(
                leftText.measuredWidthWithMargins.coerceAtLeast(icon.measuredWidth))
        setMeasuredDimension(width, height)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        leftText.layout(leftText.toHorizontalCenter(this), leftText.marginTop)
        desText.layout(desText.marginLeft, leftText.bottom+ leftText.marginBottom)
        icon.layout(leftText.left, desText.bottom )
    }
}