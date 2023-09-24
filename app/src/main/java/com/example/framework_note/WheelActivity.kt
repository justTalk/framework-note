package com.example.framework_note

import android.graphics.Color
import android.os.Bundle
import android.provider.FontRequest
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.WheelData
import kotlinx.android.synthetic.main.activity_wheel.iv_play
import kotlinx.android.synthetic.main.activity_wheel.wv_wheel
import kotlin.random.Random

class WheelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wheel)
        // 将当前 code 转换为 16 进制数
        val hex = Integer.parseInt("1F925", 16)
// 将当前 16 进制数转换成字符数组
        val chars = Character.toChars(hex)
// 将当前字符数组转换成 TextView 可加载的 String 字符串
        val mEmojiString = String(chars)
        findViewById<TextView>(R.id.emoji).setText(mEmojiString)
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