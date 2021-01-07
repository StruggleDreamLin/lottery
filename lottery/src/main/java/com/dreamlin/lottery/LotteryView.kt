package com.dreamlin.lottery

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange
import androidx.annotation.UiThread
import com.dreamlin.extension.view.DensityExtensions.getScreenWidth

/**
 * <p> Title: LotteryView </p>
 * <p> Description: </p>
 * @author  dreamlin
 * @date    1/4/21
 * @version V1.0.0
 * Created by dreamlin on 1/4/21.
 */

class LotteryView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyle: Int = 0
) : RelativeLayout(context, attributeSet, defaultStyle) {

    private val DEFAULT_WIDTH = (getScreenWidth() * 0.7f).toInt()
    private val contentView: LotteryContentView
    private var mListener: LotteryListener? = null

    //箭头位置
    private var mArrowPos: ArrowPos

    init {
        val oas =
            context.obtainStyledAttributes(attributeSet, R.styleable.LotteryView)
        val arrowPos = oas.getInt(R.styleable.LotteryView_lv_arrow_pos, 0)
        mArrowPos = when (arrowPos) {
            0 -> ArrowPos.MIDDLE
            else -> ArrowPos.TOP
        }
        val arrowRes = oas.getResourceId(R.styleable.LotteryView_lv_arrow_res, R.mipmap.icon_arrow)
//        val frameRes = oas.getResourceId(R.styleable.LotteryView_lv_outside_frame_res, -1)
        oas.recycle()

        val arrowView = ImageView(context)
        val lpArrow = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        arrowView.setImageResource(arrowRes)
        when (mArrowPos) {
            ArrowPos.MIDDLE -> {
                lpArrow.addRule(CENTER_IN_PARENT)
            }
            ArrowPos.TOP -> {
                lpArrow.addRule(ALIGN_PARENT_TOP)
                lpArrow.addRule(CENTER_HORIZONTAL)
            }
        }

        //添加contentView
        contentView = LotteryContentView(context, attributeSet, defaultStyle)
        val lpContent = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(contentView, lpContent)

        //添加arrowView 盖住Content
        addView(arrowView, lpArrow)

        /*//添加固定外框
        if (frameRes != -1) {
            val frameView = ImageView(context)
            frameView.scaleType = ImageView.ScaleType.FIT_XY
            frameView.setImageResource(frameRes)
            val lpFrame = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            addView(frameView, lpFrame)
        }*/
        //事件回调
        arrowView.setOnClickListener {
            mListener?.onStartClicked(arrowView)
        }
    }

    @UiThread
    fun initWith(builder: Builder) {
        builder.arrowPos?.let {
            this.mArrowPos = it
        }
        mListener = builder.listener
        if (builder.arrowPos == null) {
            builder.arrowPos = mArrowPos
        }
        //更新contentView
        contentView.initWith(builder)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        when (layoutParams.width) {
            LayoutParams.WRAP_CONTENT -> {
                setMeasuredDimension(DEFAULT_WIDTH, widthMeasureSpec)
            }
            else -> {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            }
        }
    }

    /**
     * 设置监听器
     */
    fun setListener(listener: LotteryListener?) {
        mListener = listener
        contentView.setListener(listener)
    }

    /**
     * 开始抽奖动画
     */
    fun startLottery(position: Int) {
        contentView.startLottery(position)
    }

    class Builder {
        //箭头位置
        var arrowPos: ArrowPos? = null

        @DrawableRes
        var arrowRes: Int? = null

        //绘制类型
        var drawerType: DrawerType? = null

        //名称字体大小 sp
        var nameTextSize: Int? = null

        //名称字体颜色
        var nameTextColor: Int? = null

        //数量字体大小
        var numberTextSize: Int? = null

        //数量字体颜色
        var numberTextColor: Int? = null

        //图标宽度 dp
        var iconWidth: Int? = null

        //图标高度 dp
        var iconHeight: Int? = null

        //名称因子 因子代表位于半径径向的百分比
        var nameFactor: Float? = null
            set(value) {
                if (value != null && value in 0f..1f) {
                    field = value
                }
            }

        //数量因子
        var numberFactor: Float? = null
            set(value) {
                if (value != null && value in 0f..1f) {
                    field = value
                }
            }

        //图标因子
        var iconFactor: Float? = null
            set(value) {
                if (value != null && value in 0f..1f) {
                    field = value
                }
            }

        //背景大图 当绘制类型为ONE_IMAGE时 必须传
        @DrawableRes
        var bgRes: Int? = null

        //是否显示数量
        var showNumber: Boolean? = true

        //动画周期
        var duration: Long? = null

        //结果停留时长
        var stopDelay: Long? = null

        //动画初始角度
        var initDegree: Int? = null

        //名称
        var names: Array<String>? = null

        //数量
        var numbers: Array<String>? = null

        //图标资源ID
        var icons: IntArray? = null

        //颜色
        var colors: IntArray? = null

        var listener: LotteryListener? = null

        /**
         * 默认转动3-20圈
         */
        fun setInitTurnCount(@IntRange(from = 3, to = 20) turnCount: Int): Builder {
            return apply {
                initDegree = 360 * turnCount
            }
        }

        fun setListener(listener: LotteryListener?): Builder {
            return apply {
                this.listener = listener
            }
        }
    }

    enum class ArrowPos {
        TOP,
        MIDDLE,
        /*   BOTTOM,
           LEFT,
           RIGHT*/
    }

    enum class DrawerType {
        ONE_IMAGE, //全部一张图
        COLORS,    //纯色扇形
        BG_IMAGE   //背景图
    }

    interface LotteryListener {
        /**
         * 当开始arrowImage被点击
         */
        fun onStartClicked(startImageView: ImageView)

        fun onLotteryStart(position: Int)

        fun onLottery(position: Int, animator: ValueAnimator)

        fun onLotteryEnd(position: Int)
    }

}
