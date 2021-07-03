package com.example.framework_note.render;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2021/6/15 09:45
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/6/15 09:45
 * @Warn: 更新说明
 * @Version: 1.0
 */
public class MyTestTextView extends androidx.appcompat.widget.AppCompatTextView {
    public MyTestTextView(@NonNull @NotNull Context context) {
        super(context);
    }

    public MyTestTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTestTextView(@NonNull @NotNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("LMM", "MyTestTextView.onDraw");
        super.onDraw(canvas);
    }
}
