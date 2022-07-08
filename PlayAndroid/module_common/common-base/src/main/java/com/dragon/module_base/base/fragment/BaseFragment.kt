package com.dragon.module_base.base.fragment

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.dragon.module_base.base.activity.BaseRouteActivity
import com.dragon.module_base.base.callback.BackPressedCallback
import com.dragon.module_base.base.callback.BackPressedOwner

/**
 * 关于处理fragment返回事件可查 https://juejin.cn/post/6890642557731078158
 *  也可参考NavHostFragment
 */

abstract class BaseFragment (@LayoutRes id: Int) : Fragment(id) {

    /**
     * 获取RouterActivity方便调用navigation进行页面切换
     */
    lateinit var myActivity: BaseRouteActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseRouteActivity) {
            //注册返回键事件
            context.backPressedDispatcherAM.addCallback(this, object : BackPressedCallback() {
                override fun handleOnBackPressed(owner: BackPressedOwner): Boolean {
                    return this@BaseFragment.handleOnBackPressed(owner)
                }
            })
            myActivity = context
        }
    }

    /**
     * 处理返回键事件
     */
    protected open fun handleOnBackPressed(owner: BackPressedOwner): Boolean {
        //默认不消耗返回键事件
        //可以重写此方法在Fragment中处理返回键事件逻辑
        return false
    }
}