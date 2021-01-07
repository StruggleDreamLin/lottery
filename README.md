# LotteryView
a flexible android lottery view

### [**中文版**](./README-ZH-CN.md)

## Results show

[![smyaMn.png](https://s3.ax1x.com/2021/01/07/smyaMn.png)](https://imgchr.com/i/smyaMn)

## How to use

### Layout

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

### Init

- with kotlin
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

- with java

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



## Download

**Attention**:This library is using kotlin to write,so make sure you are configure kotlin in your project.(How to configure kotlin with IDE; you can configure that **Tools** ==> **kotlin** ==>**configure kotlin in project** on Android Studio 4.0+)

```groovy
dependencies {
  implementation 'com.dreamlin.lottery:lottery:1.0.3'
}
```



## Attributies

| Name                | Value                       | Description                                                  |
| ------------------- | --------------------------- | ------------------------------------------------------------ |
| lv_drawer_type      | one_image\|colors\|bg_image | There are three draw types,the type one_image means that only one image contains everyting,the type colors means the item background is a color and you can appoint it for every item,the type bg_image means the view backgroud is a full image. |
| lv_arrow_pos        | top\|middle                 | The arrow pos center in view or align to top.                |
| lv_arrow_res        | R.mipmap.arrow              | The drawable res for arrow .                                 |
| lv_bg_res           | R.mipmap.lucky_base         | The drawable res for bg_image .If **lv_drawer_type** is **one_image** or **bg_image**,you have to appoint it. |
| lv_name_textSize    | 15sp                        | The item name textSize.                                      |
| lv_number_textSize  | 13sp                        | The item number textSize.                                    |
| lv_name_textColor   | @color/white                | The item name textColor.                                     |
| lv_number_textColor | @color/black                | The item number textColor.                                   |
| lv_icon_width       | 44dp                        | The item icon width.                                         |
| lv_icon_height      | 54dp                        | The item icon height.                                        |
| lv_name_factor      | 0.16 (@FloatRange(0,1f))    | The name radial factor on radius.(from top to center of circle) |
| lv_number_factor    | 0.62 (@FloatRange(0,1f))    | The number radial factor on radius.(from top to center of circle) |
| lv_icon_factor      | 0.4 (@FloatRange(0,1f))     | The icon radial factor on radius.(from top to center of circle) |
| lv_show_number      | true                        | Wheather to show numbers.                                    |
| lv_duration         | 2500L                       | The duration for lottery animation.                          |
| lv_stop_delay       | 1000L                       | The callback delay when lottery was end.                     |
| lv_init_turn_count  | 4                           | That means lottery animation will turn the four laps at least. |



- **Thanks for your star&issues.**



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

