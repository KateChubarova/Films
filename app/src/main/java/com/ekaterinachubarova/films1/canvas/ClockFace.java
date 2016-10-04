package com.ekaterinachubarova.films1.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ekaterinachubarova on 04.10.16.
 */

public class ClockFace extends View {
    private RectF rect;
    private Paint paintRed;
    private Paint paintGreen;
    private Paint paintYellow;
    private Paint paintBerge;
    private Paint paintLightGreen;

    public ClockFace(Context context) {
        super(context);
        init();
    }

    public ClockFace(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockFace(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ClockFace(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(rect, 135, 54, false, paintRed);
        canvas.drawArc(rect, 351, 54, false, paintGreen);
        canvas.drawArc(rect, 189, 54, false, paintYellow);
        canvas.drawArc(rect, 243, 54, false, paintBerge);
        canvas.drawArc(rect, 297, 54, false, paintLightGreen);

        invalidate();
    }

    private Paint getInitializePaint(int color) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(200);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    private void init() {
        rect = new RectF(150, 150, 1200, 1200);
        paintRed = getInitializePaint(Color.RED);
        paintYellow = getInitializePaint(Color.YELLOW);
        paintBerge = getInitializePaint(Color.CYAN);
        paintLightGreen = getInitializePaint(Color.BLUE);
        paintGreen = getInitializePaint(Color.GREEN);

        paintGreen.setStrokeCap(Paint.Cap.ROUND);
        paintRed.setStrokeCap(Paint.Cap.ROUND);

        paintGreen.setStrokeCap(Paint.Cap.ROUND);
    }

}
