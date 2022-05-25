package com.dragon.ft_main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
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
class MainFragment : ValueFragment(R.layout.fragment_ft_main),FragmentNavigator.Transmitter {

    @Inject
    lateinit var mainProvider: MainProvider
     lateinit var bottomController: BottomNavigationController

    private lateinit var navigator: FragmentNavigator
    override fun getNavigator(): FragmentNavigator = navigator

    private val binding by viewBinding(FragmentFtMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        navigator = FragmentNavigator.with(this,savedInstanceState)
            .initialize(binding.ftMainContainer)

        //导航结合
        bottomController = navigator.install(object : FragmentNavigator.BottomNavigation(){
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

        //        railController = navigator.install(object : FragmentNavigator.RailNavigation(){
//            override val railNavigationFragments: Map<Int, KClass<out Fragment>>
//                get() =
//                    mapOf(
//                        R.id.fragment_home to HomeFragment::class,
//                        R.id.fragment_mine to MineFragment::class,
//                        R.id.fragment_route to HomeFragment::class,
//                        R.id.fragment_playground to HomeFragment::class,
//                        R.id.fragment_square to HomeFragment::class,
//                    )
//            override val railNavigationViewId: Int
//                get() = R.id.bottom_nav
//            override val fragmentNavigationTransition = Animation.SlideVertically
//            override val fragmentViewRetentionType: ViewRetention = ViewRetention.RECREATE
//        })

    }

    //返回拦截需跳转到home页面
    override val forceBackPress: Boolean
        get() = binding.bottomNav.selectedItemId != R.id.fragment_home

    override fun onBackPressed(): Boolean {
        val binding = binding ?: return false
        if (binding.bottomNav.selectedItemId != R.id.fragment_home) {
            bottomController.select(R.id.fragment_home)
            return true
        }
        return super.onBackPressed()
    }
}