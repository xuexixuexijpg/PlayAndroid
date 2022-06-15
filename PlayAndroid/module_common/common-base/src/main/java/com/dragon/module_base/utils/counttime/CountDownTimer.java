package com.dragon.module_base.utils.counttime;

import android.os.Handler;
import android.os.SystemClock;

//https://juejin.cn/post/6991469255933820964
public class CountDownTimer {
    private String tag;
    private long mTimes;
    private long allTimes;
    private final long mCountDownInterval;
    private OnTimerCallBack mCallBack;
    private boolean isStart;
    private long startTime;
    private Handler mHandler;

    private long delay; //处理跳秒

    public CountDownTimer(String tag ,long times, long countDownInterval,Handler mHandler,OnTimerCallBack callBack) {
        this.tag = tag;
        this.mTimes = times;
        this.allTimes = times;
        this.mCountDownInterval = countDownInterval;
        this.mHandler = mHandler;
        this.mCallBack = callBack;
    }

    public void start() {

        if (isStart || mCountDownInterval <= 0) {
            return;
        }

        isStart = true;
        if (mCallBack != null) {
            mCallBack.onStart();
        }
        startTime = SystemClock.elapsedRealtime();

        if (mTimes <= 0) {
            finishCountDown();
            return;
        }

        if (delay == 0)
        mHandler.postDelayed(runnable, mCountDownInterval);
        //处理跳秒
        else mHandler.postDelayed(runnable, 1000 - delay);
    }

    /**
     * 标记
     */
    public String getTag(){
        return tag;
    }

    /**
     * 当前总时间
     */
    public long getAllTimes(){
        return allTimes;
    }

    /**
     * 当前时间
     */
    public long getCurrentTime(){
        return mTimes;
    }

    /**
     * 回调
     */
    public OnTimerCallBack getCallBack(){
        return mCallBack;
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mTimes--;
            if (mTimes > 0) {
                if (mCallBack != null) {
                    mCallBack.onTick(mTimes);
                }

                long nowTime = SystemClock.elapsedRealtime();
                delay = (nowTime - startTime) - (allTimes - mTimes) * mCountDownInterval;
                // 处理跳秒
                while (delay > mCountDownInterval) {
                    mTimes--;
                    if (mCallBack != null) {
                        mCallBack.onTick(mTimes);
                    }

                    delay -= mCountDownInterval;
                    if (mTimes <= 0) {
                        finishCountDown();
                        return;
                    }
                }
//                if (isStart){
//                    mHandler.postDelayed(this, 1000 - delay);
//                }
            } else {
                finishCountDown();
            }
        }
    };

    private void finishCountDown() {
        if (mCallBack != null) {
            mCallBack.onFinish();
        }
        isStart = false;
    }

    public void cancel() {
        isStart = false;
    }

    public void stop(){
        isStart = false;
    }

    public interface OnTimerCallBack {

        void onStart();

        void onTick(long times);

        void onFinish();

    }

}
