package com.ekaterinachubarova.films1.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekaterinachubarova.films1.R;
import com.ekaterinachubarova.films1.view.CursorClock;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ekaterinachubarova on 03.10.16.
 */

public class CanvasFragment extends Fragment {

    @BindView(R.id.cursor_clock)
    protected CursorClock cursorClock;
    @BindView(R.id.spannable_text)
    protected TextView spannableText;
    @BindView(R.id.speed)
    protected TextView speed;

    private float speedCount;
    private static  final NumberFormat formatter = new DecimalFormat("#0.00");
    private static final Random random = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater, parent, savedInstanceState);
        View v = inflater.inflate(R.layout.canva_fragment, parent, false);
        ButterKnife.bind(this,v);

        SpannableString string = new SpannableString("Speed is: ");
        string.setSpan(new ForegroundColorSpan(Color.RED), 0, 5, 0);
        string.setSpan(new RelativeSizeSpan(1.5f), 6, string.length(), 0);

        spannableText.setText(string, TextView.BufferType.SPANNABLE);

        return v;
    }

    private void randomize(){
        speedCount = random.nextFloat() * 200.0f ;
    }

    private int getColor(){
        double indexSpeedPercent = speedCount / 2;
        if (indexSpeedPercent < 20.0) {
            return Color.RED;
        } else if (indexSpeedPercent < 40.0) {
            return Color.YELLOW;
        } else if (indexSpeedPercent < 60.0) {
            return Color.CYAN;
        } else if (indexSpeedPercent < 80.0) {
            return Color.BLUE;
        } else {
            return Color.GREEN;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            randomize();
            cursorClock.startAnim(speedCount);

            SpannableString speedString = new SpannableString(formatter.format(speedCount));

            speedString.setSpan(new BackgroundColorSpan(getColor()), 0, speedString.length(), 0);
            speedString.setSpan(new ForegroundColorSpan(Color.WHITE), 0, speedString.length(), 0);

            speed.setText(speedString);
        }
    }

}
