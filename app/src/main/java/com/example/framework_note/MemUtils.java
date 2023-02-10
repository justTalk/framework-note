package com.example.framework_note;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Debug;
import android.os.Process;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * @Description: 内存相关
 * @Author: Andy
 * @CreateDate: 2021/8/16 10:34
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/8/16 10:34
 * @Warn: 更新说明
 * @Version: 1.0
 */
public class MemUtils {

  public static float[] getMemoryData(Context context) {
    try {
      Debug.MemoryInfo memInfo = null;
      float[] mem = new float[2];
      //28 为Android P
      if (Build.VERSION.SDK_INT > 28) {
        // 统计进程的内存信息 totalPss
        memInfo = new Debug.MemoryInfo();
        Debug.getMemoryInfo(memInfo);
      } else {
        //As of Android Q, for regular apps this method will only return information about the memory info for the processes running as the caller's uid;
        // no other process memory info is available and will be zero. Also of Android Q the sample rate allowed by this API is significantly limited, if called faster the limit you will receive the same data as the previous call.

        ActivityManager activityManager =
            (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        Debug.MemoryInfo[] memInfos = activityManager.getProcessMemoryInfo(new int[]{
            Process.myPid()});
        if (memInfos != null && memInfos.length > 0) {
          memInfo = memInfos[0];
        }
      }

      int totalPss = memInfo.getTotalPss();
      if (totalPss >= 0) {
        // Mem in MB
        mem[0] = totalPss / 1024.0F;
        mem[1] = memInfo.nativePss / 1024.0f;
      }
      return mem;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static ActivityManager.MemoryInfo getRam(Context context){
    ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
    ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
    activityManager.getMemoryInfo(mi);
    return mi;
  }

  /**
   * @param context
   * @return 手机当前可用内存(兆)
   */
  public static long getAvailMemory(Context context) {// 获取android当前可用内存大小
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
    am.getMemoryInfo(mi);
    return mi.availMem / 1024 / 1024;
  }
}
