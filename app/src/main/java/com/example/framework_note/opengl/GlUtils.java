package com.example.framework_note.opengl;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.res.Resources;
import android.opengl.GLES20;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2020/10/22 13:30
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/10/22 13:30
 * @Warn: 更新说明
 * @Version: 1.0
 */
public final class GlUtils {

    private static final String TAG = GlUtils.class.getSimpleName();

    public static boolean supportES2(Context context){
        if (context == null){
            return false;
        }
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            ConfigurationInfo deviceConfigurationInfo = am.getDeviceConfigurationInfo();
            boolean supportES2 = deviceConfigurationInfo.reqGlEsVersion >= 0x20000 || (
                    Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
                    && (Build.FINGERPRINT.startsWith("generic")
                       || Build.FINGERPRINT.startsWith("unknown")
                       || Build.MODEL.contains("google_sdk")
                            || Build.MODEL.contains("Emulator")
                            || Build.MODEL.contains("Android SDK built for x86"))
                    );
            return supportES2;
        }
        return false;
    }

    public static String readTextFileFromRawResource(Context context, int resId){
        if (context == null) {
            return "";
        }
        StringBuilder body = new StringBuilder();
        try{
            InputStream inputStream = context.getResources().openRawResource(resId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine;
            while ((nextLine = bufferedReader.readLine()) != null){
                body.append(nextLine);
                body.append("\n");
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (Resources.NotFoundException e){
            e.printStackTrace();
        }
        return body.toString();
    }

    public static int linkProgram(int vertecShaderObj, int fragmentShaderObj){
        int program = GLES20.glCreateProgram();
        if (program == 0) {
            Log.d(TAG, "createProgram failed -> ");
            return program;
        }
        GLES20.glAttachShader(program, vertecShaderObj);
        GLES20.glAttachShader(program, fragmentShaderObj);
        GLES20.glLinkProgram(program);
        if (!isLinked(program)) {
            GLES20.glDeleteProgram(program);
            return 0;
        }
        return program;
    }

    public static boolean isLinked(int prpgramObjId){
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(prpgramObjId, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            Log.d(TAG, "link failed -> " + linkStatus[0] + " info -> " + GLES20.glGetProgramInfoLog(prpgramObjId));
        }
        return linkStatus[0] != 0;
    }

    public static int compileShader(int type, String shader){
        //创建一个shader shaderId为创建的Opengl 中 shader对象的引用
        int shaderId = GLES20.glCreateShader(type);
        if (shaderId == 0) {
            return shaderId;
        }
        //为shader对象上传源代码 并绑定
        GLES20.glShaderSource(shaderId, shader);
        //编译shader源代码
        GLES20.glCompileShader(shaderId);
        if (!isCompileComplete(shaderId)) {
            GLES20.glDeleteShader(shaderId);
            return 0;
        }
        return shaderId;
    }

    public static boolean isCompileComplete(int shaderId){
        int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);
        boolean compileFinish = compileStatus[0] != 0;
        if (!compileFinish) {
            Log.d(TAG, "Result of shader -> " + compileStatus[0] + " info -> " + GLES20.glGetShaderInfoLog(shaderId));
        }
        return compileFinish;
    }

    public static boolean validateProgram(int programObj){
        GLES20.glValidateProgram(programObj);
        int[] programStatus = new int[1];
        GLES20.glGetProgramiv(programObj, GLES20.GL_VALIDATE_STATUS, programStatus, 0);
        Log.d(TAG, "validateProgram -> " + programStatus[0] + " info -> "
                + GLES20.glGetProgramInfoLog(programObj));
        return programStatus[0] != 0;
    }

}
