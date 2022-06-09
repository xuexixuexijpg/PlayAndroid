package com.dragon.ft_main.di.minemodule

import android.app.Activity
import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.dragon.ft_main.MainActivity
import com.dragon.ft_main.MainFragment
import com.dragon.ft_main_home.HomeArgs
import com.dragon.ft_main_home.HomeProvider
import com.dragon.ft_main_mine.MineProvider
import com.dragon.module_base.utils.NavOption
import javax.inject.Inject

class MineProviderImpl @Inject constructor(
    private val fragment: Fragment,
    private val activity: Activity
):
    MineProvider {

    override fun navigateToPage(id: Int, routePath: String?,navOptions: NavOptions?,args:Bundle?) {
        if (activity is MainActivity){
            val routeController = activity.routeControl()
            //以id的action为优先
            if (id != 0){
                routeController.navigate(id,args, navOptions?:NavOption.popUpSaveStateById(id))
                return
            }
            if (routePath != null){
                routeController.navigate(routePath, navOptions?:NavOption.popUpSaveStateByRoute(routePath))
            }
        }
    }

}