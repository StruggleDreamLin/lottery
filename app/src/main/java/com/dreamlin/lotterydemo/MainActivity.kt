package com.dreamlin.lotterydemo

import android.animation.ValueAnimator
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.dreamlin.lottery.Builder
import com.dreamlin.lottery.LotteryListener
import com.dreamlin.lottery.LotteryView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lotteryView = findViewById<LotteryView>(R.id.lotteryView)
        val size = 6
        val builder = Builder().apply {
            names = Array(size) {
                "奖品$it"
            }
            icons = IntArray(6) {
                return@IntArray R.mipmap.icon_arrow
            }
            colors = IntArray(size) {
                when (it) {
                    0, 2, 4 -> Color.GREEN
                    else -> Color.YELLOW
                }
            }
            nameTextColor = Color.parseColor("#FF7D360B")
            showNumber = false
            listener = object : LotteryListener {
                override fun onStartClicked(startImageView: ImageView) {
                    lotteryView.startLottery(Random.nextInt(size))
                }

                override fun onLotteryStart() {

                }

                override fun onLottery(animator: ValueAnimator) {

                }

                override fun onLotteryEnd() {

                }
            }
        }
        lotteryView.initWith(builder)
    }
}