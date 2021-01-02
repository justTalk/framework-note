package com.example.framework_note.class2;

import dalvik.system.PathClassLoader;

/**
 * @Description: java类作用描述
 * @Author: Andy
 * @CreateDate: 2020/12/25 18:16
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/12/25 18:16
 * @Warn: 更新说明
 * @Version: 1.0
 */
public class MyClassLoader extends ClassLoader {

    static {
        System.out.println("LMM.MyClassLoader -> " + MyClassLoader.class.hashCode());
        System.out.println("LMM.MyClassLoader classLoader-> " + MyClassLoader.class.getClassLoader());
    }
}
