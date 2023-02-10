package com.example.framework_note.ui

import android.graphics.BitmapFactory
import android.media.FaceDetector
import android.util.Log
import java.util.Arrays

/**
 *
 * @Description:     java类作用描述
 * @Author:         Andy
 * @CreateDate:     2021/11/30 16:22
 * @UpdateUser:     更新者
 * @UpdateDate:     2021/11/30 16:22
 * @Warn:   更新说明
 * @Version:        1.0
 */
object FaceDetectorTest {

  fun checkFace(path: String?){
    path?.let {
      val startTime = System.currentTimeMillis()
      val decodeFile = BitmapFactory.decodeFile(path)
      decodeFile?.let {
        val faceDetector = FaceDetector(decodeFile.width, decodeFile.height, 3)
        val arrayOfNulls = arrayOfNulls<FaceDetector.Face>(3)
        val findFaces = faceDetector.findFaces(decodeFile, arrayOfNulls)
        Log.d("LMM", "findFace: ${findFaces} cost: ${System.currentTimeMillis() - startTime}")
      }
    }
  }
}