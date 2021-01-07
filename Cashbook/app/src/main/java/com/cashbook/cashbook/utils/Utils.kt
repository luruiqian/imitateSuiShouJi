package com.cashbook.cashbook.utils

import android.content.Context
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.ImageView

object Utils{
    fun dp2px(context: Context, dipValue: Int): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dipValue.toFloat(), context.resources.displayMetrics).toInt()
    }

    fun checkZoomLevels(minZoom: Float, midZoom: Float,
                        maxZoom: Float) {
        require(minZoom < midZoom) { "Minimum zoom has to be less than Medium zoom. Call setMinimumZoom() with a more appropriate value" }
        require(midZoom < maxZoom) { "Medium zoom has to be less than Maximum zoom. Call setMaximumZoom() with a more appropriate value" }
    }

    fun hasDrawable(imageView: ImageView): Boolean {
        return imageView.drawable != null
    }

    fun isSupportedScaleType(scaleType: ImageView.ScaleType?): Boolean {
        if (scaleType == null) {
            return false
        }
        when (scaleType) {
            ImageView.ScaleType.MATRIX -> throw IllegalStateException("Matrix scale type is not supported")
        }
        return true
    }

    fun getPointerIndex(action: Int): Int {
        return action and MotionEvent.ACTION_POINTER_INDEX_MASK shr MotionEvent.ACTION_POINTER_INDEX_SHIFT
    }

}
