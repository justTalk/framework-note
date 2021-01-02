package com.example.framework_note.class2;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2020/12/25 17:48
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/25 17:48
 * @Warn: 更新说明
 * @Version: 1.0
 */
public class ClassLoader2 {
    static {
        System.out.println("LMM.ClassLoader2 -> " + ClassLoader2.class.hashCode());
        System.out.println("LMM.ClassLoader2 classLoader-> " + ClassLoader2.class.getClassLoader());
    }

    public static void a(){

    }
}
