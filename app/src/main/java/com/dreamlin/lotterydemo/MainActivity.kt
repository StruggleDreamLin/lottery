package com.dreamlin.lotterydemo

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.dreamlin.lottery.LotteryView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val lotteryView = findViewById<LotteryView>(R.id.lotteryView)
        val size = 6
        val builder = LotteryView.Builder().apply {
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
            listener = object : LotteryView.LotteryListener {
                override fun onStartClicked(startImageView: ImageView) {
                    lotteryView.startLottery(Random.nextInt(size))
                }

                override fun onLotteryStart(position: Int) {

                }

                override fun onLottery(position: Int, animator: ValueAnimator) {

                }

                override fun onLotteryEnd(position: Int) {

                }
            }
        }
        lotteryView.initWith(builder)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_color -> {
                replace(R.layout.fragment_colors, R.string.title_colors)
            }
            R.id.menu_bg_image -> {
                replace(R.layout.fragment_bg_image, R.string.title_bg_image)
            }
            R.id.menu_one_image -> {
                replace(R.layout.fragment_one_image, R.string.title_one_image)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun replace(@LayoutRes layout: Int, @StringRes title: Int) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.root,
                LotteryFragment
                    .navigation(layout, title)
            )
            .commit()
    }
}