package com.dragon.common_utils.gesture;

import android.content.Context;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * 找源码即可 源码版本不同位置可能不同
 * <a href="https://www.jianshu.com/p/4ae89e1fb36a">三指</a>
 */
public class MultiGestureMonitor {
    private final int SUPPORT_FINGER_COUNTS = 3;
    private static final float OFFSET_Y = 150;
    private float mFirstPointStartY;
    private float mSecondPointStartY;
    private float mThirdPointStartY;
    private float mStartDownY;
    private Context mContext;

    private boolean isRelease;

    private boolean only3Pointer;
    public MultiGestureMonitor(Context context) {
        mContext = context;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        if (ev == null)return false;
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mStartDownY = ev.getRawY();
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                    if (ev.getPointerCount() == SUPPORT_FINGER_COUNTS) {
                        mFirstPointStartY = ev.getY(0);
                        mSecondPointStartY = ev.getY(1);
                        mThirdPointStartY = ev.getY(2);
                        only3Pointer=true;
                    }else{
                        only3Pointer=false;
                    }
                break;
            case MotionEvent.ACTION_MOVE:

                    if (only3Pointer && ev.getPointerCount() == SUPPORT_FINGER_COUNTS) {
                        if (((Math.abs(ev.getY(0) - mFirstPointStartY)) > OFFSET_Y)
                                && ((Math.abs(ev.getY(1) - mSecondPointStartY)) > OFFSET_Y)
                                && ((Math.abs(ev.getY(2) - mThirdPointStartY)) > OFFSET_Y)) {
//                            Intent intent = new Intent("com.qucii.screenshot");
                            //Send broadcast to PhoneWindowManager to cature screen
//                            getContext().sendBroadcast(intent);
                            Toast.makeText(mContext,"截图成功",Toast.LENGTH_SHORT).show();
                            only3Pointer=false;
                        }
                        if((ev.getRawY()- mStartDownY)>5){
                            if(!isRelease){
                                return true;
                            }
                        }
                    }else{
                        if((Math.abs(ev.getRawY() - mStartDownY)) > OFFSET_Y){
                            isRelease =true;
                        }
                        break;
                    }
                break;
            case MotionEvent.ACTION_UP:
                isRelease =false;
                break;
        }

        return false;
    }

}
