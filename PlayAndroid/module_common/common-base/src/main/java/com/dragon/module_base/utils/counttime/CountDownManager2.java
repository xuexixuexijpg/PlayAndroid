package com.dragon.module_base.utils.counttime;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class CountDownManager2 {
    private static final String TAG = CountDownManager2.class.getName();
    private final static int ONE_SEC = 1000;

    private volatile static CountDownManager2 manager;

    private ArrayList<CountDownTimer> timeTasks;

    private CountDownTimer timer;
    private final Handler handler = new Handler(Looper.getMainLooper());

    private CountDownManager2() {
    }

    public static CountDownManager2 getInstance() {
        if (manager == null) {
            synchronized (CountDownManager2.class) {
                if (manager == null) {
                    manager = new CountDownManager2();
                }
            }
        }
        return manager;
    }

    public synchronized void addCountDown(long time, CountDownTimer.OnTimerCallBack callback) {
        addCountDown(UUID.randomUUID().toString(), time, callback);
    }

    public synchronized void addCountDown(String tag, long time, CountDownTimer.OnTimerCallBack callback) {
        if (callback == null) {
            // 回调为空，添加倒计时是没有意义的
            return;
        }
        if (isAdded(tag)) {
            return;
        }
        if (time < 0){
            callback.onTick(time);
            return;
        }

        CountDownTimer timeEntity = new CountDownTimer(tag, time, ONE_SEC,handler,callback);
        if (timeTasks == null) {
            timeTasks = new ArrayList<>();
        }
        timeTasks.add(timeEntity);

        // 触发倒计时开始
        startCountDown();
    }

    private boolean isAdded(String tag) {
        if (timeTasks == null || TextUtils.isEmpty(tag)) {
            return false;
        }
        for (CountDownTimer e : timeTasks) {
            String eTag = e.getTag();
            if (!TextUtils.isEmpty(eTag)) {
                if (eTag.equals(tag)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 开始倒计时
    private void startCountDown() {
        removeTask();
    }

    private void removeTask() {
        if (timeTasks != null) {
            Iterator<CountDownTimer> iterator = timeTasks.iterator();

            //轮询
            while (iterator.hasNext()) {
                CountDownTimer next = iterator.next();
                //开始任务
                next.start();
                long currentTime = next.getCurrentTime();
                //倒计时完成 移除任务
                if (currentTime <= 0){
                    iterator.remove();
                }


//                if (time > currentTime) {
//                    currentTime++;
//                    next.setCurrentTime(currentTime);
//                    callBackOnTime(callback, currentTime);
//                } else if (time == currentTime) {
//                    callBackOnTime(callback, currentTime);
//                    callBackTimeUp(callback);
//                    iterator.remove();
//                } else {
//                    iterator.remove();
//                }
            }
        }

        // 如果队列为空，则暂停
        if (timeTasks == null || timeTasks.size() == 0) {
            stopCountDown();
        }
    }


    // 结束倒计时
    private void stopCountDown() {
        if (timer != null){
            timer.cancel();
            timer = null;
        }

        if (timeTasks != null){
            timeTasks.clear();
            timeTasks = null;
        }
    }

    public void destroy() {
        stopCountDown();
        handler.removeCallbacksAndMessages(null);
        manager = null;
    }

    // 根据tag删除对应的任务
    public void cancelTime(String tag) {
        if (timeTasks == null || TextUtils.isEmpty(tag)) {
            return;
        }
        if (timeTasks != null) {
            Iterator<CountDownTimer> iterator = timeTasks.iterator();
            while (iterator.hasNext()) {
                CountDownTimer next = iterator.next();
                String eTag = next.getTag();
                if (!TextUtils.isEmpty(eTag)) {
                    if (eTag.equals(tag)) {
                        iterator.remove();
                    }
                }
            }
        }
    }
}
