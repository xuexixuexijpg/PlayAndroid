package com.dargon.playandroid.init;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

//后台监听 https://juejin.cn/post/7111516772141891620#heading-4
public class ForegroundCheck implements Application.ActivityLifecycleCallbacks {

    //单例
    private static final ForegroundCheck instance = new ForegroundCheck();

    private static final int CHECK_DELAY = 500;

    //用于判断是否程序在前台
    private boolean foreground = false, paused = true;
    //handler用于处理切换activity时的短暂时期可能出现的判断错误
    private final Handler handler = new Handler();
    private Runnable check;

    public static void init(Application app) {
        app.registerActivityLifecycleCallbacks(instance);
    }

    public static ForegroundCheck get() {
        return instance;
    }

    private ForegroundCheck() {
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, Bundle savedInstanceState) {
        Log.e("测试创建", "onActivityCreated: "+ activity.getLocalClassName() );
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        paused = true;
        if (check != null)
            handler.removeCallbacks(check);

        handler.postDelayed(check = () -> {
            if (foreground && paused) {
                foreground = false;
            }
        }, CHECK_DELAY);

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        paused = false;
        foreground = true;
        if (check != null)
            handler.removeCallbacks(check);

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if (count == 0) {
            //后台切换到前台
            Log.e("测试前台", "onActivityStarted: "+ activity.getLocalClassName() );
        }
        count++;
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        count--;
        if (count == 0) {
            //前台切换到后台
            Log.e("测试后台", "onActivityStarted: "+ activity.getLocalClassName() );
        }
    }

    //记录Activity的总个数，用于判断监听是在前后还是后台
    public int count = 0;

    /**
     * 判断当前应用在前台还是在后台，如果在后台会启动推送
     */
    public boolean isForeground() {
        return foreground;
    }

}
