package com.dragon.module_base.base.activity

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.mvrx.MavericksView
import com.dragon.module_base.base.callback.BackPressedDispatcher
import com.dragon.module_base.base.callback.BackPressedOwner

/**
 *
 * 单Activity架构中导航直接用这个
 */
abstract class BaseRouteActivity(@LayoutRes id: Int) : AppCompatActivity(id), MavericksView, BackPressedOwner {

    //返回键分发器
    private val _backPressedDispatcher = BackPressedDispatcher(this)
    /**
     * 获取返回键分发器
     */
    val backPressedDispatcherAM: BackPressedDispatcher get() = _backPressedDispatcher


    override fun invalidate() {

    }

    override fun onBackPressed() {
        kotlin.runCatching {
            if (backPressedDispatcherAM.onBackPressed())
                return
        }
        invokeSuperBackPressed()
    }

    override fun invokeSuperBackPressed() {
        super.onBackPressed()
    }

}