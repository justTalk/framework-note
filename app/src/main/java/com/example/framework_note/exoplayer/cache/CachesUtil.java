package com.example.framework_note.exoplayer.cache;

import android.content.Context;
import android.util.Log;
import java.io.File;

public class CachesUtil {

     public static String VIDEO = "video";
     
    /**
     * 获取媒体缓存文件
     *
     * @param child
     * @return
     */
    public static File getMediaCacheFile(String child, Context context) {
        String directoryPath = context.getFilesDir().getAbsolutePath() + File.separator + child;
        File file = new File(directoryPath);
        //判断文件目录是否存在
        if (!file.exists()) {
            file.mkdirs();
        }
        Log.d("LMM", "getMediaCacheFile ====> " + directoryPath);
        return file;
    }
}