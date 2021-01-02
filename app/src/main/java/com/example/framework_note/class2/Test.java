package com.example.framework_note.class2;

import com.example.framework_note.class2.ClassLoader2;

public class Test {
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
}