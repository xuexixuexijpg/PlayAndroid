package com.dragon.common_utils.ext

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * 生命周期与 Application 一样长的协程，可以做一些后台作业
 */
val globalScope by lazy {
    CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
}

/**
 * @desc：绑定生命周期的 Handler
 * @since：2021-06-01 11:31
 */
class LifecycleHandler(
    lifecycleOwner: LifecycleOwner,
    looper: Looper = Looper.getMainLooper(),
    callback: Callback? = null,
) : Handler(looper, callback), DefaultLifecycleObserver {

    private val mLifecycleOwner: LifecycleOwner = lifecycleOwner

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        removeCallbacksAndMessages(null)
        mLifecycleOwner.lifecycle.removeObserver(this)
    }
}