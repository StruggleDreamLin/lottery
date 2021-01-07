package com.dreamlin.lotterydemo;

import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.dreamlin.lottery.LotteryView;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * <p> Title: LotteryFragment </p>
 * <p> Description: </p>
 *
 * @author dreamlin
 * @version V1.0.0
 * Created by dreamlin on 1/7/21.
 * @date 1/7/21
 */

public class LotteryFragment extends androidx.fragment.app.Fragment {

    public int layout;
    public int title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(layout, container, false);
        init(root);
        return root;
    }

    private void init(View rootView) {
        ActionBar actionBar = getActivity().getActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
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
    }

    public static LotteryFragment navigation(@LayoutRes int layout, @StringRes int title) {
        LotteryFragment fragment = new LotteryFragment();
        fragment.layout = layout;
        fragment.title = title;
        return fragment;
    }
}
