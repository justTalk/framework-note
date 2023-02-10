package com.example.framework_note;

import android.util.Log;
import androidx.annotation.NonNull;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2021/11/3 18:02
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/11/3 18:02
 * @Warn: 更新说明
 * @Version: 1.0
 */
public class OomCatchHandler implements Thread.UncaughtExceptionHandler {

  private Thread.UncaughtExceptionHandler defaultHandler;

  public OomCatchHandler(){
    defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
  }

  @Override
  public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
    Log.d("LMM", "uncaughtException: " + t.getName() + " throw: " + e.toString());
    if (e instanceof OutOfMemoryError) {
      Log.d("LMM", "oom:");
    }
    if (defaultHandler != null) {
      defaultHandler.uncaughtException(t, e);
    }
  }
}
