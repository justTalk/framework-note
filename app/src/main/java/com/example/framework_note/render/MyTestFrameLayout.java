package com.example.framework_note.render;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2021/6/15 09:44
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/6/15 09:44
 * @Warn: 更新说明
 * @Version: 1.0
 */
public class MyTestFrameLayout extends FrameLayout {

    public MyTestFrameLayout(@NonNull Context context) {
        super(context);
    }

    public MyTestFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTestFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyTestFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.d("LMM", "MyTestFrameLayout.dispatchDraw");
        super.dispatchDraw(canvas);
    }
}
