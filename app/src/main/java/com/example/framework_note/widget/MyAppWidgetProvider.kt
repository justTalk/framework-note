package com.example.framework_note.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import com.example.framework_note.R

/**
 *
 * @Description:     java类作用描述
 * @Author:         Andy
 * @CreateDate:     2020/12/25 15:47
 * @UpdateUser:     更新者
 * @UpdateDate:     2020/12/25 15:47
 * @Warn:   更新说明
 * @Version:        1.0
 */
class MyAppWidgetProvider : AppWidgetProvider() {

    var t: Int = 0

    fun updateAppWidget(context: Context?,
                        appWidgetManager: AppWidgetManager?,
                        appWidgetIds: Int){
        Log.d("LMM", "onUpdate: -> $appWidgetIds")
        val remoteView = RemoteViews(context?.packageName, R.layout.remote_layout)
        remoteView.setTextViewText(R.id.textView4, "小组件 " + t)
        appWidgetManager?.updateAppWidget(appWidgetIds, remoteView)
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetIds?.forEach {
            updateAppWidget(context, appWidgetManager, it)
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
        Log.d("LMM", "onAppWidgetOptionsChanged: -> $appWidgetId")
    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        Log.d("LMM", "onDeleted")
    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        Log.d("LMM", "onEnabled")
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.d("LMM", "onReceive")
    }

    override fun onRestored(context: Context?, oldWidgetIds: IntArray?, newWidgetIds: IntArray?) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
        Log.d("LMM", "onRestored")
    }
}