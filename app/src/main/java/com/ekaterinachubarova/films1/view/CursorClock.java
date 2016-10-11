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

    private int middleCornerX;
    private int middleCornerY;

    private int leftCornerX;
    private int leftCornerY;

    private int rightCornerY;
    private int rightCornerX;

    private double alpha;

    private Measurement measure;
    private double rMiddle;
    private double rLeft;
    private double rRight;


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

    public CursorClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        middleCornerX = (int) (measure.getCenterX() + rMiddle * Math.cos(getAlphaDegree(alpha)));
        middleCornerY = (int) (measure.getCenterY() + rMiddle * Math.sin(getAlphaDegree(alpha)));

        leftCornerX = (int) (measure.getCenterX() + rLeft * Math.cos(getAlphaDegree(alpha + 4)));
        leftCornerY = (int) (measure.getCenterY() + rLeft * Math.sin(getAlphaDegree(alpha + 4)));

        rightCornerX = (int) (measure.getCenterX() + rRight * Math.cos(getAlphaDegree(alpha - 4)));
        rightCornerY = (int) (measure.getCenterY() + rRight * Math.sin(getAlphaDegree(alpha - 4)));

        Path path = new Path();

        path.moveTo(middleCornerX, middleCornerY);
        path.lineTo(leftCornerX, leftCornerY);
        path.lineTo(rightCornerX, rightCornerY);

        canvas.drawPath(path, paint);
        invalidate();
    }

    private void init() {
        measure = Measurement.newInstance();
        paint = new Paint();

        rLeft = measure.getR() + 200;
        rRight = measure.getR() + 200;
        rMiddle = measure.getR() + 100;

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(50);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.FILL);
    }

    private double getAlphaDegree(double x) {
        return Math.toRadians(x + 135);
    }

    public void startAnim (double degrees) {
        ValueAnimator va = ValueAnimator.ofFloat(0, (float)degrees);
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
