package com.ekaterinachubarova.films1.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ekaterinachubarova on 04.10.16.
 */

public class CursorClock extends View {
    private Paint paint;
    private Path path;
    private RectF rect;
    public CursorClock(Context context) {
        super(context);
        init ();
    }
    public CursorClock(Context context, AttributeSet attrs) {
        super(context, attrs);
        init ();
    }

    public CursorClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init ();
    }

    public CursorClock(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        path.moveTo(160.0f, 240.0f);
        path.lineTo(140.0f, 200.0f);
        path.addArc(rect, -180, 180);
        path.lineTo(160.0f, 240.0f);
        path.close();

        canvas.drawPath(path, paint);

        invalidate();
    }

    private void init (){
        paint = new Paint();
        path = new Path();
        rect = new RectF(140, 180, 180, 220);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }
}
