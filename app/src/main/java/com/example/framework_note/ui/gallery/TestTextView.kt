package com.example.framework_note.ui.gallery

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent

/**
 *
 * @Description:     java类作用描述
 * @Author:         Andy
 * @CreateDate:     2021/5/7 09:09
 * @UpdateUser:     更新者
 * @UpdateDate:     2021/5/7 09:09
 * @Warn:   更新说明
 * @Version:        1.0
 */
class TestTextView(context: Context?, attrs: AttributeSet) : androidx.appcompat.widget.AppCompatTextView(
    context!!, attrs) {

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var handled = super.onTouchEvent(event)
        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                Log.d("TestTextView", "ACTION_DOWN ${handled} + ${hashCode()}")
            }
            MotionEvent.ACTION_MOVE -> {
                if(event?.getY(0) > 150){
                    //handled = false
                }
                Log.d("TestTextView", "ACTION_MOVE ${handled} + ${hashCode()}")
            }
            MotionEvent.ACTION_UP -> {Log.d("TestTextView", "ACTION_UP ${handled} + ${hashCode()}")}
            MotionEvent.ACTION_CANCEL -> {Log.d("TestTextView", "ACTION_CANCEL ${handled} + ${hashCode()}")}
        }
        return handled
    }
}