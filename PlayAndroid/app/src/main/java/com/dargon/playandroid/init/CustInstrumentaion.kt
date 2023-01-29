package com.dargon.playandroid.init

import android.app.Activity
import android.app.Application
import android.app.Instrumentation
import android.app.UiAutomation
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.os.*
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import com.dargon.playandroid.MainActivity

/**
 * 自定义Instrumentaion
 */
class CustInstrumentaion : Instrumentation() {

    // ActivityThread中原始的对象, 保存起来
    private lateinit var mBase : Instrumentation;

    public fun setOriginInstrumentation(base:Instrumentation){
        this.mBase = base
    }

    override fun onCreate(arguments: Bundle?) {
        mBase.onCreate(arguments)
    }

    override fun start() {
        mBase.start()
    }

    override fun onStart() {
        mBase.onStart()
    }

    override fun onException(obj: Any?, e: Throwable?): Boolean {
        return mBase.onException(obj, e)
    }

    override fun sendStatus(resultCode: Int, results: Bundle?) {
        mBase.sendStatus(resultCode, results)
    }

    override fun addResults(results: Bundle?) {
        super.addResults(results)
    }

    override fun finish(resultCode: Int, results: Bundle?) {
        mBase.finish(resultCode, results)
    }

    override fun setAutomaticPerformanceSnapshots() {
        mBase.setAutomaticPerformanceSnapshots()
    }

    override fun startPerformanceSnapshot() {
        mBase.startPerformanceSnapshot()
    }

    override fun endPerformanceSnapshot() {
        mBase.endPerformanceSnapshot()
    }

    override fun onDestroy() {
        mBase.onDestroy()
    }

    override fun getContext(): Context {
        return mBase.getContext()
    }

    override fun getComponentName(): ComponentName {
        return mBase.getComponentName()
    }

    override fun getTargetContext(): Context {
        return mBase.getTargetContext()
    }

    override fun getProcessName(): String {
        return super.getProcessName()
    }

    override fun isProfiling(): Boolean {
        return mBase.isProfiling()
    }

    override fun startProfiling() {
        mBase.startProfiling()
    }

    override fun stopProfiling() {
        mBase.stopProfiling()
    }

    override fun setInTouchMode(inTouch: Boolean) {
        mBase.setInTouchMode(inTouch)
    }

    override fun resetInTouchMode() {
        super.resetInTouchMode()
    }

    override fun waitForIdle(recipient: Runnable?) {
        mBase.waitForIdle(recipient)
    }

    override fun waitForIdleSync() {
        mBase.waitForIdleSync()
    }

    override fun runOnMainSync(runner: Runnable?) {
        mBase.runOnMainSync(runner)
    }

    override fun startActivitySync(intent: Intent?): Activity {
        return mBase.startActivitySync(intent)
    }

    override fun startActivitySync(intent: Intent, options: Bundle?): Activity {
        return super.startActivitySync(intent, options)
    }

    override fun addMonitor(monitor: ActivityMonitor?) {
        mBase.addMonitor(monitor)
    }

    override fun addMonitor(
        filter: IntentFilter?,
        result: ActivityResult?,
        block: Boolean
    ): ActivityMonitor {
        return mBase.addMonitor(filter, result, block)
    }

    override fun addMonitor(
        cls: String?,
        result: ActivityResult?,
        block: Boolean
    ): ActivityMonitor {
        return mBase.addMonitor(cls, result, block)
    }

    override fun checkMonitorHit(monitor: ActivityMonitor?, minHits: Int): Boolean {
        return mBase.checkMonitorHit(monitor, minHits)
    }

    override fun waitForMonitor(monitor: ActivityMonitor?): Activity {
        return mBase.waitForMonitor(monitor)
    }

    override fun waitForMonitorWithTimeout(monitor: ActivityMonitor?, timeOut: Long): Activity {
        return mBase.waitForMonitorWithTimeout(monitor, timeOut)
    }

    override fun removeMonitor(monitor: ActivityMonitor?) {
        mBase.removeMonitor(monitor)
    }

    override fun invokeMenuActionSync(targetActivity: Activity?, id: Int, flag: Int): Boolean {
        return mBase.invokeMenuActionSync(targetActivity, id, flag)
    }

    override fun invokeContextMenuAction(targetActivity: Activity?, id: Int, flag: Int): Boolean {
        return mBase.invokeContextMenuAction(targetActivity, id, flag)
    }

    override fun sendStringSync(text: String?) {
        mBase.sendStringSync(text)
    }

    override fun sendKeySync(event: KeyEvent?) {
        mBase.sendKeySync(event)
    }

    override fun sendKeyDownUpSync(keyCode: Int) {
        mBase.sendKeyDownUpSync(keyCode)
    }

    override fun sendCharacterSync(keyCode: Int) {
        mBase.sendCharacterSync(keyCode)
    }

    override fun sendPointerSync(event: MotionEvent?) {
        mBase.sendPointerSync(event)
    }

    override fun sendTrackballEventSync(event: MotionEvent?) {
        mBase.sendTrackballEventSync(event)
    }

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return mBase.newApplication(cl, className, context)
    }

    override fun callApplicationOnCreate(app: Application?) {
        mBase.callApplicationOnCreate(app)
    }

    override fun newActivity(
        clazz: Class<*>?,
        context: Context?,
        token: IBinder?,
        application: Application?,
        intent: Intent?,
        info: ActivityInfo?,
        title: CharSequence?,
        parent: Activity?,
        id: String?,
        lastNonConfigurationInstance: Any?
    ): Activity {
        Log.e("测试", "newActivity: ${clazz?.name}", )
        if (clazz != null && clazz.name == MainActivity::class.java.name) {
            return mBase.newActivity(
                EmptyActivity::class.java,
                context,
                token,
                application,
                intent,
                info,
                title,
                parent,
                id,
                lastNonConfigurationInstance
            )
        } else
            return mBase.newActivity(
                clazz,
                context,
                token,
                application,
                intent,
                info,
                title,
                parent,
                id,
                lastNonConfigurationInstance
            )
    }

    override fun newActivity(cl: ClassLoader?, className: String?, intent: Intent?): Activity {
        Log.e("测试", "newActivity: $className")
        return if (className != null && className == MainActivity::class.java.name) {
            mBase.newActivity(cl, EmptyActivity::class.java.name, intent)
        } else
            mBase.newActivity(cl, className, intent)
    }

    override fun callActivityOnCreate(activity: Activity?, icicle: Bundle?) {
        mBase.callActivityOnCreate(activity, icicle)
    }

    override fun callActivityOnCreate(
        activity: Activity?,
        icicle: Bundle?,
        persistentState: PersistableBundle?
    ) {
        mBase.callActivityOnCreate(activity, icicle, persistentState)
    }

    override fun callActivityOnDestroy(activity: Activity?) {
        mBase.callActivityOnDestroy(activity)
    }

    override fun callActivityOnRestoreInstanceState(
        activity: Activity,
        savedInstanceState: Bundle
    ) {
        mBase.callActivityOnRestoreInstanceState(activity, savedInstanceState)
    }

    override fun callActivityOnRestoreInstanceState(
        activity: Activity,
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        mBase.callActivityOnRestoreInstanceState(activity, savedInstanceState, persistentState)
    }

    override fun callActivityOnPostCreate(activity: Activity, savedInstanceState: Bundle?) {
        mBase.callActivityOnPostCreate(activity, savedInstanceState)
    }

    override fun callActivityOnPostCreate(
        activity: Activity,
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        mBase.callActivityOnPostCreate(activity, savedInstanceState, persistentState)
    }

    override fun callActivityOnNewIntent(activity: Activity?, intent: Intent?) {
        mBase.callActivityOnNewIntent(activity, intent)
    }

    override fun callActivityOnStart(activity: Activity?) {
        mBase.callActivityOnStart(activity)
    }

    override fun callActivityOnRestart(activity: Activity?) {
        mBase.callActivityOnRestart(activity)
    }

    override fun callActivityOnResume(activity: Activity?) {
        mBase.callActivityOnResume(activity)
    }

    override fun callActivityOnStop(activity: Activity?) {
        mBase.callActivityOnStop(activity)
    }

    override fun callActivityOnSaveInstanceState(activity: Activity, outState: Bundle) {
        mBase.callActivityOnSaveInstanceState(activity, outState)
    }

    override fun callActivityOnSaveInstanceState(
        activity: Activity,
        outState: Bundle,
        outPersistentState: PersistableBundle
    ) {
        mBase.callActivityOnSaveInstanceState(activity, outState, outPersistentState)
    }

    override fun callActivityOnPause(activity: Activity?) {
        mBase.callActivityOnPause(activity)
    }

    override fun callActivityOnUserLeaving(activity: Activity?) {
        super.callActivityOnUserLeaving(activity)
    }

    override fun callActivityOnPictureInPictureRequested(activity: Activity) {
        super.callActivityOnPictureInPictureRequested(activity)
    }

    override fun startAllocCounting() {
        mBase.startAllocCounting()
    }

    override fun stopAllocCounting() {
        mBase.stopAllocCounting()
    }

    override fun getAllocCounts(): Bundle {
        return mBase.getAllocCounts()
    }

    override fun getBinderCounts(): Bundle {
        return mBase.getBinderCounts()
    }

    override fun getUiAutomation(): UiAutomation {
        return mBase.getUiAutomation()
    }

    override fun getUiAutomation(flags: Int): UiAutomation {
        return super.getUiAutomation(flags)
    }

    override fun acquireLooperManager(looper: Looper?): TestLooperManager {
        return super.acquireLooperManager(looper)
    }
}