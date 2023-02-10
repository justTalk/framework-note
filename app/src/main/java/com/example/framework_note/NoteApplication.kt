package com.example.framework_note

import android.app.Application

/**
 *
 * @Description:     java类作用描述
 * @Author:         Andy
 * @CreateDate:     2021/11/3 18:01
 * @UpdateUser:     更新者
 * @UpdateDate:     2021/11/3 18:01
 * @Warn:   更新说明
 * @Version:        1.0
 */
class NoteApplication: Application() {

  override fun onCreate() {
    super.onCreate()
    Thread.setDefaultUncaughtExceptionHandler(OomCatchHandler())
  }
}