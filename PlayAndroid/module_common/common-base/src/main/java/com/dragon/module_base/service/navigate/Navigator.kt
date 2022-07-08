package com.dragon.module_base.service.navigate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.dragon.module_base.base.activity.BaseRouteActivity

//导航
interface Navigator{

    fun navigateToDeepLink(activity: FragmentActivity,routePath: String, navOptions: NavOptions? = null, args: Bundle?){
        if (activity is BaseRouteActivity){
            activity.navigateDeepLink(routePath,navOptions, args)
        }
    }
}