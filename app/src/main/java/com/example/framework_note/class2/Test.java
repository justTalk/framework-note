package com.example.framework_note.class2;

import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.framework_note.class2.ClassLoader2;

public class Test {
    private final int N = 10;
    static {
        i = 0;  // 给变量赋值可以正常编译通过
        System.out.println("LMM.Test classLoader-> " + Test.class.hashCode());
        System.out.println("LMM.Test classLoader-> " + Test.class.getClassLoader());
    }
    public static int i = 1;

    public static void a(){
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> aClass = null;
        try {
            aClass = myClassLoader.loadClass("com.example.framework_note.class2.ClassLoader2");
            System.out.println("LMM.Test a aClass-> " + aClass);
            System.out.println("LMM.Test a aClass-> " + aClass.getClassLoader());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("LMM.Test a aClass-> ClassNotFoundException");
        }
    }


    public static void testThreadCrash(){
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
                Log.d("LMM", "uncaughtException-> " +t.getName());
                e.printStackTrace();
                Log.d("LMM", "uncaughtException-> ");
            }
        });
        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                testThreadMain();
            }

            @Override
            public void destroy() {
                super.destroy();
                Log.d("LMM", "thread destory");
            }
        };
        thread.setName("sub Thread");
        thread.start();

    }


    public static void testThreadMain(){
        int a = 100;
        if (Thread.currentThread().getId() != Looper.getMainLooper().getThread().getId()) {
            a = getI(null);
            Log.d("LMM", "getI-> " + a);
        }
        Log.d("LMM", "testThreadMain->" + a );
    }

    private static int getI(Test test){
        if (test != null) {
            return 1;
        }
        return test.N;
    }
}