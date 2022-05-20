package com.dragon.ft_main

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.ft_main.databinding.FragmentFtMainBinding
import com.dragon.ft_main_home.HomeFragment
import com.kpstv.navigation.Destination
import com.kpstv.navigation.FragmentNavigator
import com.kpstv.navigation.ValueFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * 主页
 */
@AndroidEntryPoint
class MainFragment : ValueFragment(R.layout.fragment_ft_main),FragmentNavigator.Transmitter {

    @Inject
    lateinit var mainProvider: MainProvider

    private lateinit var navigator: FragmentNavigator
    override fun getNavigator(): FragmentNavigator = navigator

    private val binding by viewBinding(FragmentFtMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //初始化
        navigator = FragmentNavigator.with(this,savedInstanceState)
            .initialize(binding.ftMainContainer, Destination.of(com.dragon.ft_main_home.HomeFragment::class))
    }

}