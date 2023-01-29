package com.dargon.playandroid.init;

import android.app.Instrumentation;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * https://blog.csdn.net/H28496/article/details/52534027
 * 注入自己的
 */
public class HookManager {
    /* -------------------------------------------------------------
                                            Fields
  ------------------------------------------------------------- */
    static Class<?> ActivityThread;
    static Method ActivityThread_method_currentActivityThread;
    static Object obj_activityThread;
    static Method AssetManager_method_addAssetPath;


    /* -------------------------------------------------------------
                                        Static Methods
    ------------------------------------------------------------- */
    /**
     * 初始化操作，获得一些基本的类、变量、方法等。
     */
    public static void init() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // 获得ActivityThread类
        ActivityThread = Class.forName("android.app.ActivityThread");
        // 获得ActivityThread#currentActivityThread()方法
        ActivityThread_method_currentActivityThread = ActivityThread.getDeclaredMethod("currentActivityThread");

        // 根据currentActivityThread方法获得ActivityThread对象
        obj_activityThread = ActivityThread_method_currentActivityThread.invoke(ActivityThread);
        Log.e("测试", "init: "+ obj_activityThread );

        // 获得AssetManager#addAssetPath()方法
//        AssetManager_method_addAssetPath = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
//        AssetManager_method_addAssetPath.setAccessible(true);
    }

    /**
     * 注入Sona的Instrumentation
     */
    public static void injectInstrumentation() throws NoSuchFieldException, IllegalAccessException {
        Log.i("测试","开始注入Sona的Instrumentation。");
        // 获得ActivityThread类中的Instrumentation字段
        Field field_instrumentation = obj_activityThread.getClass().getDeclaredField("mInstrumentation");
        field_instrumentation.setAccessible(true);
        // 创建出一个新的Instrumentation
        CustInstrumentaion obj_custom_instrumentation = new CustInstrumentaion();
        Instrumentation o = (Instrumentation)field_instrumentation.get(obj_custom_instrumentation);
        obj_custom_instrumentation.setOriginInstrumentation(o);
        // 用Instrumentation字段注入Sona的Instrumentation变量
        field_instrumentation.set(obj_activityThread, obj_custom_instrumentation);
    }
}
