package com.scam.editor.common.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2021/5/10 16:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/5/10 16:48
 * @Warn: 更新说明
 * @Version: 1.0
 */
public class ScollerView extends View {

    private float lastX;
    private float lastY;
    private Paint paint = new Paint();

    public ScollerView(Context context) {
        super(context);
    }

    public ScollerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setClickable(true);
        setEnabled(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                int y = (int) (event.getY() - lastY);
                int x = (int) (event.getX() - lastX);
                scrollBy(-x, 0);
                invalidate();
                lastX = event.getX();
                lastY = event.getY();
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(4);
        canvas.drawLine(0 + getScrollX(), getBottom(), getRight() + getScrollX(), getTop(), paint);
        Log.d("LMM", "onDraw: left: "+ getLeft() + " right: " + getRight() + " scrollX: " +getScrollX());
    }
}
