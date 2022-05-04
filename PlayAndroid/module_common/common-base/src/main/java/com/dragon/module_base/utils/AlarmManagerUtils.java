package com.dragon.module_base.utils;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

//参考https://zhuanlan.zhihu.com/p/129613784
// google的jetpack有workManager库来做定时任务，替换以前的方法；已封装好适配多机型且方便
public class AlarmManagerUtils {
    private static final long TIME_INTERVAL = 10 * 1000;//闹钟执行任务的时间间隔
    private Context context;
    public static AlarmManager am;
    public static PendingIntent pendingIntent;

    private Calendar calendar;
    private Boolean isOnceSet = false; //只设置一次

    //
    private AlarmManagerUtils(Context aContext) {
        this.context = aContext;
    }

    //singleton
    private static AlarmManagerUtils instance = null;

    public static AlarmManagerUtils getInstance(Context aContext) {
        if (instance == null) {
            synchronized (AlarmManagerUtils.class) {
                if (instance == null) {
                    instance = new AlarmManagerUtils(aContext);
                }
            }
        }
        return instance;
    }

    //    public void createGetUpAlarmManager() {
//        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, MyService.class);
//        pendingIntent = PendingIntent.getService(context, 0, intent, 0);//每隔10秒启动一次服务
//    }
    public AlarmManager getAm() {
        return am;
    }

    @SuppressLint({"NewApi", "ShortAlarm"})
    public void getUpAlarmManagerStartWork(Intent intent, int day) {
        //版本适配 System.currentTimeMillis()
        long days = day * (24 * 60 * 60 * 1000);
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        if (am == null)
        am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (pendingIntent == null)
        pendingIntent = PendingIntent.getBroadcast(context, 1001, intent, 0);
        am.cancel(pendingIntent);
        if (System.currentTimeMillis() > calendar.getTimeInMillis()) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0及以上
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() + days, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4及以上
            am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    pendingIntent);
        } else {
            am.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), TIME_INTERVAL, pendingIntent);
        }

    }


    public void getUpAlarmManagerStartWork(Intent intent, Class<?> receiverClass) {
        //版本适配 System.currentTimeMillis()
        //适配8.0以上（不然没法发出广播）
//        if (DeviceUtil.getBuildLevel() > Build.VERSION_CODES.O) {
            intent.setComponent(new ComponentName(context, receiverClass));
//        }
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        if (am == null)
            am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (pendingIntent == null)
            pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 1, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        am.cancel(pendingIntent);
//        if (System.currentTimeMillis() > calendar.getTimeInMillis()) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0及以上
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() , pendingIntent);
        } else {// 4.4及以上
            am.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    pendingIntent);
        }

//        Log.e("AlarmTaskReceiver", "getUpAlarmManagerStartWork: "+ DateUtil.longToDateStr(calendar.getTimeInMillis()));
    }

    @SuppressLint("NewApi")
    public void getUpAlarmManagerWorkOnOthers() {
        //高版本重复设置闹钟达到低版本中setRepeating相同效果
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {// 6.0及以上
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    System.currentTimeMillis() + TIME_INTERVAL, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {// 4.4及以上
            am.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                    + TIME_INTERVAL, pendingIntent);
        }
    }

    public void cancel() {
        try {
            am.cancel(pendingIntent);
        } catch (Exception e) {
            Log.e("TAG", "cancel: " + e);
        }
    }
}