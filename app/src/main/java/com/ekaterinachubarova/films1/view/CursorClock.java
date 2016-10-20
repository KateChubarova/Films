package com.ekaterinachubarova.films1.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ekaterinachubarova on 04.10.16.
 */

public class CursorClock extends View{
    private Paint paint;

    private float alpha;

    private Measurement measure;
    private float rMiddle;
    private float rLeft;
    private float rRight;


    public CursorClock(Context context) {
        super(context);
        init();
    }

    public CursorClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CursorClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {

        int middleCornerX = (int) (measure.getCenterX() + rMiddle * Math.cos(getAlphaDegree(alpha)));
        int middleCornerY = (int) (measure.getCenterY() + rMiddle * Math.sin(getAlphaDegree(alpha)));

        int leftCornerX = (int) (measure.getCenterX() + rLeft * Math.cos(getAlphaDegree(alpha + 2)));
        int leftCornerY = (int) (measure.getCenterY() + rLeft * Math.sin(getAlphaDegree(alpha + 2)));

        int rightCornerX = (int) (measure.getCenterX() + rRight * Math.cos(getAlphaDegree(alpha - 2)));
        int rightCornerY = (int) (measure.getCenterY() + rRight * Math.sin(getAlphaDegree(alpha - 2)));

        Path path = new Path();

        path.moveTo(middleCornerX, middleCornerY);
        path.lineTo(leftCornerX, leftCornerY);
        path.lineTo(rightCornerX, rightCornerY);

        canvas.drawPath(path, paint);
    }

    private void init() {
        measure = Measurement.newInstance();
        paint = new Paint();

        rLeft = measure.getR() + 120;
        rRight = measure.getR() + 120;
        rMiddle = measure.getR() + 80;

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(50);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL);
    }

    private double getAlphaDegree(float x) {
        return Math.toRadians(x + 135);
    }

    public void startAnim (float degrees) {
        ValueAnimator va = ValueAnimator.ofFloat(0, degrees);
        int mDuration = 3000;
        va.setDuration(mDuration);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                alpha = (Float) animation.getAnimatedValue();
                invalidate();
            }
        });

        va.start();
    }
}
