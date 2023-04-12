package com.example.framework_note

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.WheelData
import kotlinx.android.synthetic.main.activity_wheel.iv_play
import kotlinx.android.synthetic.main.activity_wheel.wv_wheel
import kotlin.random.Random

class WheelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wheel)

        val arrayListOf = arrayListOf<WheelData>()
        arrayListOf.add(WheelData(0,Color.parseColor("#69AEFF"), ""))
        arrayListOf.add(WheelData(1,Color.parseColor("#FFFFFF"), ""))
        arrayListOf.add(WheelData(2,Color.parseColor("#F66C57"), ""))
        wv_wheel.setData(arrayListOf)
        iv_play.setOnClickListener {
            var p = Random(System.currentTimeMillis()).nextInt(6)
            wv_wheel.rotate(p)
        }
    }
}