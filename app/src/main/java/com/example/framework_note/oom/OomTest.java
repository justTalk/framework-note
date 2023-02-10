package com.example.framework_note.oom;

import java.util.Map;
import java.util.Random;
import javassist.CannotCompileException;
import javassist.ClassPool;

/**
 * @Description: https://tianmingxing.com/2019/11/17/%E4%BB%8B%E7%BB%8DJVM%E4%B8%ADOOM%E7%9A%848%E7%A7%8D%E7%B1%BB%E5%9E%8B/
 * @Author: Andy
 * @CreateDate: 2021/11/3 19:42
 * @UpdateUser: 更新者
 * @UpdateDate: 2021/11/3 19:42
 * @Warn: 更新说明
 * @Version: 1.0
 */
public class OomTest {

  private static final int GB_1 = 1024 * 1024 * 1024;
  static javassist.ClassPool cp = javassist.ClassPool.getDefault();
  /**
   * java.lang.OutOfMemoryError: Failed to allocate a 4294967312 byte allocation with 6291456 free bytes and 381MB until OOM, target footprint 8915312, growth limit 402653184
   *
   */
  public static int[] allocate(){
    int[] intArray = new int[GB_1];
    for (int i = 0; i < intArray.length; i++) {
      intArray[i] = i;
    }
    return intArray;
  }

  public static void gcOverheadLimitExceeded(){
    Map map = System.getProperties();
    Random r = new Random();
    while (true) {
      map.put(r.nextInt(), "value");
    }
  }

  public static void generatePermGenOom() {
    for (int i = 0; i < 100_000_000; i++) {
      ClassPool pool = ClassPool.getDefault();
      try {
        pool.makeClass("eu.plumbr.demo.Generated" + i).toClass();
      } catch (CannotCompileException e) {
        e.printStackTrace();
      }
    }
  }

  public static void metaSpaceOom(){
    for (int i = 0; ; i++) {
      try {
        Class c = cp.makeClass("eu.plumbr.demo.Generated" + i).toClass();
      } catch (CannotCompileException e) {
        e.printStackTrace();
      }
    }
  }

  public static void newNativeThreadOom(){
    while(true){
      new Thread(new Runnable(){
        public void run() {
          try {
            Thread.sleep(10000000);
          } catch(InterruptedException e) { }
        }
      }).start();
    }
  }
}
