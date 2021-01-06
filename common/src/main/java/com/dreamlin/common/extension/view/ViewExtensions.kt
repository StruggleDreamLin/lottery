package com.dreamlin.common.extension.view

import android.graphics.drawable.BitmapDrawable
import android.view.View
import android.widget.ImageView

/**
 * <p> Title: ViewExtensions </p>
 * <p> Description: </p>
 * @author  dreamlin
 * @date    12/18/20
 * @version V1.0.0
 * Created by dreamlin on 12/18/20.
 */
object ViewExtensions {

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }
}

object ImageViewExtensions {

    /**
     * 标记ImageView src Bitmap资源为可回收状态
     * 注意并非立即回收
     */
    fun ImageView.recycleBitmap() {
        val bitmapDrawable = drawable as? BitmapDrawable?
        val bitmap = bitmapDrawable?.bitmap
        bitmap?.recycle()
    }

    /**
     * 回收ImageView background 资源
     * 注意并非立即回收
     */
    fun ImageView.recycleBackground() {
        val bitmapDrawable = background as? BitmapDrawable?
        val bitmap = bitmapDrawable?.bitmap
        bitmap?.recycle()
    }

    /**
     * 回收ImageView所占用资源
     */
    fun ImageView.recycle() {
        this.recycleBitmap()
        this.recycleBackground()
    }

}