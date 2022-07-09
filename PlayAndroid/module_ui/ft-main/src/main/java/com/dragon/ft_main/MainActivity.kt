package com.dragon.ft_main


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dragon.common_utils.mmkvutil.MMKVOwner
import com.dragon.common_utils.ui.StatusBarUtil
import com.dragon.module_base.base.activity.BaseRouteActivity
import com.dragon.web_browser.helper.WebViewManager
import com.dylanc.longan.*

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseRouteActivity(R.layout.activity_main), MMKVOwner {

    private var exitTime = 0L

    override fun controllerId() = R.id.fragmentNav

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        transparentStatusBar()
        StatusBarUtil.setStatusBarDarkTheme(this,true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        WebViewManager.destroy()
    }

    override fun onBackPressed() {
        if (routeControl().currentDestination?.id == R.id.mainFragment) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                exitTime = System.currentTimeMillis()
                val msg = "再按一次返回桌面"
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                return
            } else {
                moveTaskToBack(true)
            }
        }else {
            super.onBackPressed()
        }
    }

}
