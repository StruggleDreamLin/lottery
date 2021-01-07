package com.dreamlin.extension.view

import android.content.res.Resources
import android.util.TypedValue

/**
 * <p> Title: DensityExtensions </p>
 * <p> Description: </p>
 * @author  dreamlin
 * @date    12/18/20
 * @version V1.0.0
 * Created by dreamlin on 12/18/20.
 */
object DensityExtensions {

    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }

    fun Float.dp2px(): Float {
        val displayMetrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics)
    }

    fun Int.dp2px(): Float {
        val displayMetrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), displayMetrics)
    }

    fun Int.sp2px(): Float {
        val displayMetrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), displayMetrics)
    }

    fun Float.sp2px(): Float {
        val displayMetrics = Resources.getSystem().displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), displayMetrics)
    }

    fun Int.px2dp(): Int {
        val density = Resources.getSystem().displayMetrics.density
        return ((this / density) + 0.5f).toInt()
    }

    fun Int.px2sp(): Int {
        val density = Resources.getSystem().displayMetrics.scaledDensity
        return ((this / density) + 0.5f).toInt()
    }

}