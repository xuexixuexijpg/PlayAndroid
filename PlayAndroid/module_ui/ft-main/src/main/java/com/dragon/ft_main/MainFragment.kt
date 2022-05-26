package com.dragon.ft_main

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.IntegerRes
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowLayoutInfo
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.ft_main.databinding.FragmentFtMainBinding
import com.dragon.ft_main_home.HomeFragment
import com.dragon.ft_main_mine.MineFragment
import com.kpstv.navigation.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.reflect.KClass

/**
 * 主页
 */
@AndroidEntryPoint
class MainFragment : ValueFragment(R.layout.fragment_ft_main), FragmentNavigator.Transmitter {

    @Inject
    lateinit var mainProvider: MainProvider
    lateinit var bottomController: BottomNavigationController
    lateinit var railController: RailNavigationController

    //导航控制类型
    private var navType = 1

    //配置信息
    private lateinit var configuration: Configuration

    private lateinit var navigator: FragmentNavigator
    override fun getNavigator(): FragmentNavigator = navigator

    private val binding by viewBinding(FragmentFtMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        navigator = FragmentNavigator.with(this, savedInstanceState)
            .initialize(binding.ftMainContainer)
        configuration = resources.configuration

        //底部导航结合
        bottomController = navigator.install(object : FragmentNavigator.BottomNavigation() {
            override val bottomNavigationFragments: Map<Int, KClass<out Fragment>>
                get() =
                    mapOf(
                        R.id.fragment_home to HomeFragment::class,
                        R.id.fragment_mine to MineFragment::class,
                        R.id.fragment_route to HomeFragment::class,
                        R.id.fragment_playground to HomeFragment::class,
                        R.id.fragment_square to HomeFragment::class,
                    )
            override val bottomNavigationViewId: Int
                get() = R.id.bottom_nav
            override val fragmentNavigationTransition = Animation.Fade
            override val fragmentViewRetentionType: ViewRetention = ViewRetention.RECREATE
        })

        //侧边导航
        railController = navigator.install(object : FragmentNavigator.RailNavigation() {
            override val railNavigationFragments: Map<Int, KClass<out Fragment>>
                get() =
                    mapOf(
                        R.id.fragment_home to HomeFragment::class,
                        R.id.fragment_mine to MineFragment::class,
                        R.id.fragment_route to HomeFragment::class,
                        R.id.fragment_playground to HomeFragment::class,
                        R.id.fragment_square to HomeFragment::class,
                    )
            override val railNavigationViewId: Int
                get() = R.id.rail_nav
            override val fragmentNavigationTransition = Animation.Fade
            override val fragmentViewRetentionType: ViewRetention = ViewRetention.RECREATE
        })

    }

    //返回拦截需跳转到home页面
    override val forceBackPress: Boolean
        get() = if (binding.bottomNav.visibility ==View.VISIBLE){
            binding.bottomNav.selectedItemId != R.id.fragment_home
        }else{
            binding.railNav.selectedItemId != R.id.fragment_home
        }

    override fun onBackPressed(): Boolean {
        if (getNavigator().getCurrentFragment() != HomeFragment::class) {
            if (binding.bottomNav.visibility ==View.VISIBLE){
                bottomController.select(R.id.fragment_home)
                //需要保持选中一致
                binding.railNav.selectedItemId = R.id.fragment_home
            }else {
                railController.select(R.id.fragment_home)
                binding.bottomNav.selectedItemId = R.id.fragment_home
            }
            return true
        }
        return super.onBackPressed()
    }

    /**
     * 暴露的内部导航操作
     */
    fun navTo(navControlType: Int,@IdRes navDestination: Int){
        if (navType == 1){
            bottomController.select(navDestination)
        }else{
            bottomController.select(navDestination)
        }
    }

    /**
     * 拿到窗口信息并更新UI界面
     */
    fun setLayoutInfo(newLayoutInfo: WindowLayoutInfo) {
        //横屏状态
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){

        }
        //w小于600
        if (configuration.screenWidthDp < 600) {
            navType = 1
            mainProvider.setNavControlType(1)
            binding.bottomNav.visibility = View.VISIBLE
            binding.railNav.visibility = View.GONE
            //设置选中状态以保持一致
            binding.bottomNav.selectedItemId = binding.railNav.selectedItemId
        } else {
            var hasRestView = true
            val layoutInfoList = newLayoutInfo.displayFeatures
            layoutInfoList.forEach {
                //折叠屏 api好像可以获取到折叠两边的屏幕信息
                if (it is FoldingFeature) {
                    hasRestView = false
                }
            }
            //不是折叠屏
            if (hasRestView) {
                navType = 2
                mainProvider.setNavControlType(2)
                binding.bottomNav.visibility = View.GONE
                binding.railNav.visibility = View.VISIBLE
                binding.railNav.selectedItemId = binding.bottomNav.selectedItemId
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        Log.e("SSSS", "onDestroyView: ", )
        super.onDestroyView()
    }
}