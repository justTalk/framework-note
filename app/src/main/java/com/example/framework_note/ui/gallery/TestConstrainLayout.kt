package com.example.framework_note.ui.gallery

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

/**
 *
 * @Description:     java类作用描述
 * @Author:         Andy
 * @CreateDate:     2021/5/7 09:16
 * @UpdateUser:     更新者
 * @UpdateDate:     2021/5/7 09:16
 * @Warn:   更新说明
 * @Version:        1.0
 */
class TestConstrainLayout(context: Context, attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet) {

    private var lastX: Float = 0f
    private var lastY: Float = 0f

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val handled = super.onTouchEvent(event)
        event?.action ?: return false
        val offsetX = event.x - lastX
        val offsetY = event.y - lastY
        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                Log.d("TestConstrainLayout", "ACTION_DOWN ${handled}")}
            MotionEvent.ACTION_MOVE -> {
                //getChildAt(0).offsetLeftAndRight(offsetX.toInt())
                //getChildAt(0).offsetTopAndBottom(offsetY.toInt())
                Log.d("TestConstrainLayout", "ACTION_MOVE ${handled}")}
            MotionEvent.ACTION_UP -> {
                Log.d("TestConstrainLayout", "ACTION_UP ${handled}")}
            MotionEvent.ACTION_CANCEL -> {
                Log.d("TestConstrainLayout", "ACTION_CANCEL ${handled}")}
        }
        lastX = event.x
        lastY = event.y
        return handled
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        Log.d("TestConstrainLayout", "dispatchTouchEvent ${ev?.getY(0)}")

        return super.dispatchTouchEvent(ev)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val y: Float = ev?.getY(0) ?: 0f
        if (y > 1200) {
            Log.d("TestConstrainLayout", "onInterceptTouchEvent ${ev?.getY(0)}")
            return true
        }else{
            return super.onInterceptTouchEvent(ev)
        }
    }
}