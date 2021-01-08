# LotteryView
一款可灵活配置的Android 抽奖View

### [**English**](./README.md)

## 效果

[![smyaMn.png](https://s3.ax1x.com/2021/01/07/smyaMn.png)](https://imgchr.com/i/smyaMn)

## 使用

- 布局

```xml
<com.dreamlin.lottery.LotteryView
    android:id="@+id/lotteryView"
    android:layout_width="300dp"
    android:layout_height="300dp"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:lv_arrow_res="@mipmap/icon_arrow"
    app:lv_drawer_type="colors" />
```

### 初始化

- Kotlin

```kotlin
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
```

- Java

```java
final LotteryView lotteryView = rootView.findViewById(R.id.lotteryView);
final int size = 6;
String[] names = new String[size];
String[] numbers = new String[size];
int[] icons = new int[size];
int[] colors = new int[size];
for (int i = 0; i < size; i++) {
    names[i] = "奖品" + i;
    numbers[i] = "x" + i;
    icons[i] = R.mipmap.icon_arrow;
    if (i % 2 == 0) {
        colors[i] = Color.YELLOW;
    } else {
        colors[i] = Color.GREEN;
    }
}
LotteryView.Builder builder = new LotteryView.Builder();
builder.setNames(names);
builder.setIcons(icons);
builder.setNumbers(numbers);
builder.setColors(colors);
//初始化LotteryView with configs
lotteryView.initWith(builder);
final Random random = new Random();
lotteryView.setListener(new LotteryView.LotteryListener() {
    @Override
    public void onStartClicked(@NotNull ImageView startImageView) {
        lotteryView.startLottery(random.nextInt(size));
    }

    @Override
    public void onLotteryStart(int position) {

    }

    @Override
    public void onLottery(int position, @NotNull ValueAnimator animator) {

    }

    @Override
    public void onLotteryEnd(int position) {

    }
});
```



## 引用

注意:该View使用Kotlin语言编写，你可以通过IDE在你的工程里配置Kotlin依赖; **Tools** ==> **kotlin** ==>**configure kotlin in project** on Android Studio 4.0+)

```groovy
dependencies {
  implementation 'com.dreamlin.lottery:lottery:1.0.4'
}
```



## 属性

| Name                | Value                       | Description                                                  |
| ------------------- | --------------------------- | ------------------------------------------------------------ |
| lv_drawer_type      | one_image\|colors\|bg_image | 支持三种类型，one_image 代表 一张图包含所有内容；colors 代表背景为纯色，可以为每一种奖品指定不同的扇形颜色背景；bg_image 代表背景为图片，图片会缩放铺满整个LotteryView。 |
| lv_arrow_pos        | top\|middle                 | 指针位置，middle 代表正中心，top代表上方中间位置。           |
| lv_arrow_res        | R.mipmap.arrow              | 指针资源。                                                   |
| lv_bg_res           | R.mipmap.lucky_base         | 背景图资源，当drawer_type为 **one_image** 和 **bg_image**时必须指定，否则会抛出异常。 |
| lv_name_textSize    | 15sp                        | 奖品名称字体大小。                                           |
| lv_number_textSize  | 13sp                        | 奖品数量字体大小。                                           |
| lv_name_textColor   | @color/white                | 奖品名称字体颜色。                                           |
| lv_number_textColor | @color/black                | 奖品数量字体颜色。                                           |
| lv_icon_width       | 44dp                        | 奖品图标宽度。                                               |
| lv_icon_height      | 54dp                        | 奖品图标高度                                                 |
| lv_name_factor      | 0.16 (@FloatRange(0,1f))    | 奖品名称 径向百分比。                                        |
| lv_number_factor    | 0.62 (@FloatRange(0,1f))    | 奖品数量 径向百分比。                                        |
| lv_icon_factor      | 0.4 (@FloatRange(0,1f))     | 奖品图标 径向百分比。                                        |
| lv_show_number      | true                        | 是否显示数量。                                               |
| lv_duration         | 2500L                       | 开奖动画周期。                                               |
| lv_stop_delay       | 1000L                       | 结果展示停留时长。                                           |
| lv_init_turn_count  | 4                           | 动画至少旋转几圈。                                           |

关于factor:

[![smNZXF.png](https://s3.ax1x.com/2021/01/07/smNZXF.png)](https://imgchr.com/i/smNZXF)

- **欢迎 star&issues.**



## License

```kotlin
MIT License
Copyright (c) 2021 dreamlin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```

