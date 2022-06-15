//package com.dragon.module_base.utils.counttime;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.text.TextUtils;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// * 只是方便统一管理而已
// */
//
////https://www.jb51.net/article/228968.htm
////https://github.com/ZGaoFei/CountDown 从这copy过来的
////https://juejin.cn/post/7002894370068234253
////https://juejin.cn/post/6984725689257689101
//public class CountDownManager {
//    private static final String TAG = CountDownManager.class.getName();
//    private final static int ONE_SEC = 1000;
//
//    private volatile static CountDownManager manager;
//
//    private ArrayList<TimeEntity> timeTasks;
//
//    private Timer timer;
//    private final Handler handler = new Handler(Looper.getMainLooper());
//
//    private CountDownManager() {
//    }
//
//    public static CountDownManager getInstance() {
//        if (manager == null) {
//            synchronized (CountDownManager.class) {
//                if (manager == null) {
//                    manager = new CountDownManager();
//                }
//            }
//        }
//        return manager;
//    }
//
//    public void addCountDown(long time, TimeCallback callback) {
//        addCountDown("", time, callback);
//    }
//
//    public void addCountDown(String tag, long time, TimeCallback callback) {
//        if (callback == null) {
//            // 回调为空，添加倒计时是没有意义的
//            return;
//        }
//        if (isAdded(tag)) {
//            return;
//        }
//        if (time < 0){
//            callback.onTime(time);
//            return;
//        }
//
//        TimeEntity timeEntity = new TimeEntity(tag, time, callback);
//        if (timeTasks == null) {
//            timeTasks = new ArrayList<>();
//        }
//        timeTasks.add(timeEntity);
//
//        // 触发倒计时开始
//        startCountDown();
//    }
//
//    private boolean isAdded(String tag) {
//        if (timeTasks == null || TextUtils.isEmpty(tag)) {
//            return false;
//        }
//        for (TimeEntity e : timeTasks) {
//            String eTag = e.getTag();
//            if (!TextUtils.isEmpty(eTag)) {
//                if (eTag.equals(tag)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    // 开始倒计时
//    private void startCountDown() {
//        if (timer == null) {
//            timer = new Timer(TAG);
//            TimerTask task = new TimerTask() {
//                @Override
//                public void run() {
//                    removeTask();
//                }
//            };
//            timer.schedule(task, 0, ONE_SEC);
//        }
//    }
//
//    private void removeTask() {
//        if (timeTasks != null) {
//            Iterator<TimeEntity> iterator = timeTasks.iterator();
//            while (iterator.hasNext()) {
//                TimeEntity next = iterator.next();
//                long currentTime = next.getCurrentTime();
//                long time = next.getTime();
//                TimeCallback callback = next.getCallback();
//
//                if (time > currentTime) {
//                    currentTime++;
//                    next.setCurrentTime(currentTime);
//                    callBackOnTime(callback, currentTime);
//                } else if (time == currentTime) {
//                    callBackOnTime(callback, currentTime);
//                    callBackTimeUp(callback);
//
//                    iterator.remove();
//                } else {
//                    iterator.remove();
//                }
//            }
//        }
//
//        // 如果队列为空，则暂停
//        if (timeTasks == null || timeTasks.size() == 0) {
//            stopCountDown();
//        }
//    }
//
//    private void callBackOnTime(TimeCallback callback, long time) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                callback.onTime(time);
//            }
//        });
//    }
//
//    private void callBackTimeUp(TimeCallback callback) {
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                callback.timeUp();
//            }
//        });
//    }
//
//    // 结束倒计时
//    private void stopCountDown() {
//        if (timer != null){
//            timer.cancel();
//            timer = null;
//        }
//
//        if (timeTasks != null){
//            timeTasks.clear();
//            timeTasks = null;
//        }
//    }
//
//    public void destroy() {
//        stopCountDown();
//        handler.removeCallbacksAndMessages(null);
//        manager = null;
//    }
//
//    // 根据tag删除对应的任务
//    public void cancelTime(String tag) {
//        if (timeTasks == null || TextUtils.isEmpty(tag)) {
//            return;
//        }
//
//        if (timeTasks != null) {
//            Iterator<TimeEntity> iterator = timeTasks.iterator();
//            while (iterator.hasNext()) {
//                TimeEntity next = iterator.next();
//                String eTag = next.getTag();
//                if (!TextUtils.isEmpty(eTag)) {
//                    if (eTag.equals(tag)) {
//                        iterator.remove();
//                    }
//                }
//            }
//        }
//    }
//
//}
