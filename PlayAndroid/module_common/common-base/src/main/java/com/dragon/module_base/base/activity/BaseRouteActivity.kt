package com.dragon.module_base.base.activity

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.dragon.module_base.R
import com.dragon.module_base.base.callback.BackPressedDispatcher
import com.dragon.module_base.base.callback.BackPressedOwner
import com.dragon.module_base.service.navigate.NavOption.navigateOption
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.regex.Pattern

/**
 *
 * 单Activity架构中导航直接用这个
 */
abstract class BaseRouteActivity(@LayoutRes id: Int) : AppCompatActivity(id), BackPressedOwner {

    //返回键分发器
    private val _backPressedDispatcher = BackPressedDispatcher(this)

    private lateinit var navController: NavController

    /**
     * 获取返回键分发器
     */
    val backPressedDispatcherAM: BackPressedDispatcher get() = _backPressedDispatcher

    /**
     * NavController的视图id
     */
    abstract fun controllerId(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(controllerId())
        if (navHostFragment != null) {
            //不先在布局中
            navController = navHostFragment.findNavController()
        }
    }

    /**
     * 将导航返出去使用
     */
    fun routeControl():NavController{
        return navController
    }

    /**
     * 通过正则匹配“{}”内的参数并替换
     */
    fun navigateDeepLink(deepLink: String, navOptions: NavOptions?, args: Bundle? = null) {
        var newDeepLink = "https://playandroid.dragon.com/$deepLink"
        args?.apply {
            val matcher = Pattern.compile("(\\{)(.+?)(\\})").matcher(newDeepLink)
            while (matcher.find()) {
                val key = matcher.group(2)
                if (containsKey(key)) {
                    val newUrl = Uri.encode(get(key).toString())
                    newDeepLink = newDeepLink.replace("{$key}", newUrl)
                }
            }
        }
        if (navOptions != null){
            navController.navigate(Uri.parse(newDeepLink), navOptions,)
        }else{
            navController.navigate(Uri.parse(newDeepLink), navigateOption())
        }
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