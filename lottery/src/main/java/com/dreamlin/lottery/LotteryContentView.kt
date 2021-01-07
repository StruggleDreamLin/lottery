package com.dreamlin.lottery

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.UiThread
import com.dreamlin.extension.view.DensityExtensions.sp2px
import com.dreamlin.common.extension.view.getBitmap


/**
 * <p> Title: LotteryView </p>
 * <p> Description: </p>
 * @author  dreamlin
 * @date    1/4/21
 * @version V1.0.0
 * Created by dreamlin on 1/4/21.
 */
class LotteryContentView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyle: Int = 0
) : View(context, attributeSet, defaultStyle) {

    //绘制类型
    private var mDrawerType: LotteryView.DrawerType

    //名称字体大小 sp
    private var mNameTextSize: Int

    //名称字体颜色
    private var mNameTextColor: Int

    //数量字体大小
    private var mNumberTextSize: Int

    //数量字体颜色
    private var mNumberTextColor: Int

    //图标宽度 dp
    private var mIconWidth: Int

    //图标高度 dp
    private var mIconHeight: Int

    //名称因子 因子代表位于半径径向的百分比
    private var mNameFactor: Float

    //数量因子
    private var mNumberFactor: Float

    //图标因子
    private var mIconFactor: Float

    //背景大图 当绘制类型为ONE_IMAGE时 必须传
    @DrawableRes
    private var mBgRes: Int? = null

    //背景大图
    private var mBgBitmap: Bitmap? = null

    //是否显示数量
    private var mShowNumber: Boolean = true

    //动画周期
    private var mDuration = 2500L

    //动画周期结束后的结果停留时长
    private var mStopDelay: Long = 1000L

    //动画初始角度
    private var mInitDegree = 360.shl(2)

    //名称
    private var mNames: Array<String>? = null

    //数量
    private var mNumbers: Array<String>? = null

    //图标资源ID
    private var mIcons: IntArray? = null

    //纯色扇形颜色
    private var mColors: IntArray? = null

    //图片资源
    private var mBitmaps = mutableListOf<Bitmap?>()

    private var mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var iconRectF: RectF? = null

    private var bgRectF: RectF? = null

    //半径
    private var mRadius = 0

    //角度
    private var mAngle = 0f

    //是否正在抽奖
    private var isLottery = false

    //监听器
    private var mListener: LotteryView.LotteryListener? = null

    //开奖index
    private var position: Int = 0

    //动画监听
    private val mAnimatorListener: Animator.AnimatorListener
    private val mAnimatorUpdateListener: ValueAnimator.AnimatorUpdateListener

    init {
        val oas = context.obtainStyledAttributes(attributeSet, R.styleable.LotteryView)
        mDrawerType = when (oas.getInt(R.styleable.LotteryView_lv_drawer_type, 2)) {
            1 -> LotteryView.DrawerType.ONE_IMAGE
            3 -> LotteryView.DrawerType.BG_IMAGE
            else -> LotteryView.DrawerType.COLORS
        }
        mBgRes = oas.getResourceId(R.styleable.LotteryView_lv_bg_res, R.mipmap.lucky_base)
        mNameTextSize =
            oas.getDimensionPixelSize(R.styleable.LotteryView_lv_name_textSize, 14.sp2px().toInt())
        mNumberTextSize = oas.getDimensionPixelSize(
            R.styleable.LotteryView_lv_number_textSize,
            14.sp2px().toInt()
        )
        mNameTextColor = oas.getColor(R.styleable.LotteryView_lv_name_textColor, Color.RED)
        mNumberTextColor = oas.getColor(R.styleable.LotteryView_lv_number_textColor, Color.RED)
        mIconWidth = oas.getDimension(R.styleable.LotteryView_lv_icon_width, -1f).toInt()
        mIconHeight = oas.getDimension(R.styleable.LotteryView_lv_icon_height, -1f).toInt()
        mNameFactor = oas.getFloat(R.styleable.LotteryView_lv_name_factor, 0.16f)
        mNumberFactor = oas.getFloat(R.styleable.LotteryView_lv_number_factor, 0.62f)
        mIconFactor = oas.getFloat(R.styleable.LotteryView_lv_icon_factor, 0.4f)
        mShowNumber = oas.getBoolean(R.styleable.LotteryView_lv_show_number, true)
        mDuration = oas.getInt(R.styleable.LotteryView_lv_duration, 2500).toLong()
        mStopDelay = oas.getInt(R.styleable.LotteryView_lv_stop_delay, 1000).toLong()
        val turnCount = oas.getInt(R.styleable.LotteryView_lv_init_turn_count, 4)
        mInitDegree = 360 * turnCount
        val size = oas.getInt(R.styleable.LotteryView_lv_item_size, 0)
        if (size > 0) {
            mAngle = 360f / size
        }
        oas.recycle()

        mAnimatorListener = object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                isLottery = true
                mListener?.onLotteryStart(position)
            }

            override fun onAnimationEnd(animation: Animator?) {
                postDelayed({
                    mListener?.onLotteryEnd(position)
                    rotation = 0f
                    isLottery = false
                }, mStopDelay)
            }

            override fun onAnimationCancel(animation: Animator?) {
                isLottery = false
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        }
        mAnimatorUpdateListener = ValueAnimator.AnimatorUpdateListener {
            mListener?.onLottery(position, it)
        }
    }

    @UiThread
    fun initWith(builder: LotteryView.Builder) {
        builder.run {
            drawerType?.let {
                mDrawerType = it
            }
            nameTextSize?.let {
                mNameTextSize = it
            }
            nameTextColor?.let {
                mNameTextColor = it
            }
            numberTextSize?.let {
                mNumberTextSize = it
            }
            numberTextColor?.let {
                mNumberTextColor = it
            }
            iconWidth?.let {
                mIconWidth = it
            }
            iconHeight?.let {
                mIconHeight = it
            }
            nameFactor?.let {
                mNameFactor = it
            }
            numberFactor?.let {
                mNumberFactor = it
            }
            iconFactor?.let {
                mIconFactor = it
            }
            bgRes?.let {
                mBgRes = it
            }
            showNumber?.let {
                mShowNumber = it
            }
            duration?.let {
                mDuration = it
            }
            stopDelay?.let {
                mStopDelay = it
            }
            initDegree?.let {
                mInitDegree = it
            }
            icons?.let {
                mIcons = it
                mAngle = 360f / it.size
            }
            colors?.let {
                mColors = it
            }
            mNames = names
            numbers?.let {
                mNumbers = it
            }
            mListener = listener
        }
        onInit(width, height)
        invalidate()
    }

    private fun onInit(w: Int, h: Int) {
        mRadius = w.shr(1)
        //如果还未测量完成 直接return
        if (mRadius == 0) return
        if (mIconWidth == -1) { //如果未指定图标大小
            mIconWidth = (0.2f * mRadius).toInt()
        }
        if (mIconHeight == -1) {
            mIconWidth = (0.3f * mRadius).toInt()
        }

        mBitmaps.clear()
        mIcons?.forEachIndexed { index, resId ->
            val bitmap = getBitmap(resources, resId, mIconWidth)
            mBitmaps.add(index, bitmap)
        }
        if (mDrawerType != LotteryView.DrawerType.COLORS) {
            if (mBgRes == null) {
                throw RuntimeException("未设置背景图bgRes资源，请在布局或代码中进行设置")
            } else {
                mBgBitmap = getBitmap(resources, mBgRes!!, w)
            }
        }
        if (mDrawerType != LotteryView.DrawerType.ONE_IMAGE) {
            val left = (width - mIconWidth).shr(1).toFloat()
            val right = (width + mIconWidth).shr(1).toFloat()
            val top = mRadius * mIconFactor - mIconWidth.shr(1)
            val bottom = mRadius * mIconFactor + mIconWidth.shr(1)
            if (iconRectF == null) {
                iconRectF = RectF(left, top, right, bottom)
            } else {
                iconRectF?.set(left, top, right, bottom)
            }
        }
        if (bgRectF == null) {
            bgRectF = RectF(0f, 0f, w.toFloat(), h.toFloat())
        } else {
            bgRectF?.set(0f, 0f, w.toFloat(), h.toFloat())
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        onInit(w, h)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            when (mDrawerType) {
                LotteryView.DrawerType.ONE_IMAGE -> {
                    drawBitmap(mBgBitmap!!, null, bgRectF!!, mPaint)
                }
                LotteryView.DrawerType.COLORS -> {
                    drawContent(this)
                }
                LotteryView.DrawerType.BG_IMAGE -> {
                    drawBitmap(mBgBitmap!!, null, bgRectF!!, mPaint)
                    drawContent(this)
                }
            }
        }
    }

    /**
     * 绘制内容
     */
    private fun drawContent(canvas: Canvas) {
        val textBounds = Rect()
        val centerX = width.shr(1).toFloat()
        val centerY = height.shr(1).toFloat()
        for (i in mBitmaps.indices) {
            //画扇形
            if (mDrawerType == LotteryView.DrawerType.COLORS) {
                mColors?.get(i)?.let {
                    mPaint.color = it
                    val startAngle = 270f - mAngle / 2
                    canvas.drawArc(bgRectF!!, startAngle, mAngle, true, mPaint)
                }
            }

            //画图标
            mBitmaps[i]?.let {
                iconRectF?.let { rectF ->
                    canvas.drawBitmap(it, null, rectF, mPaint)
                }
            }
            //画name
            mNames?.get(i)?.let {
                mPaint.textSize = mNameTextSize.toFloat()
                mPaint.color = mNameTextColor
                mPaint.getTextBounds(it, 0, it.length, textBounds)
                val textX = (width - (textBounds.right - textBounds.left)).shr(1).toFloat()
                val textY = mRadius * mNameFactor + (textBounds.bottom - textBounds.top).shr(1)
                canvas.drawText(it, textX, textY, mPaint)
            }

            if (mShowNumber) { //画数量
                mNumbers?.get(i)?.let {
                    mPaint.textSize = mNumberTextSize.toFloat()
                    mPaint.color = mNumberTextColor
                    mPaint.getTextBounds(it, 0, it.length, textBounds)
                    val textX = (width - (textBounds.right - textBounds.left)).shr(1).toFloat()
                    val textY =
                        mRadius * mNumberFactor + (textBounds.bottom - textBounds.top).shr(1)
                    canvas.drawText(it, textX, textY, mPaint)
                }
            }
            canvas.rotate(mAngle, centerX, centerY)
        }
    }

    /**
     * 设置动画监听器
     */
    fun setListener(listener: LotteryView.LotteryListener?) {
        mListener = listener
    }

    /**
     * 开始抽奖动画
     */
    fun startLottery(position: Int) {
        if (isLottery) return
        this.position = position
        val targetDegrees = mInitDegree + 360 - position * mAngle
        val animator = animate().setDuration(mDuration)
            .rotation(targetDegrees)
            .setListener(mAnimatorListener)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            animator.setUpdateListener(mAnimatorUpdateListener)
        }
        animator.start()
    }
}