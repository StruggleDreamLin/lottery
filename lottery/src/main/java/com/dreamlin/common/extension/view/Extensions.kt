package com.dreamlin.common.extension.view

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.DrawableRes

/**
 * <p> Title: Extensions </p>
 * <p> Description: </p>
 * @author  dreamlin
 * @date    1/4/21
 * @version V1.0.0
 * Created by dreamlin on 1/4/21.
 */

fun getBitmap(resources: Resources, @DrawableRes resId: Int, width: Int): Bitmap? {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(resources, resId, options)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = width
    options.inScaled = true
    return BitmapFactory.decodeResource(resources, resId, options)
}