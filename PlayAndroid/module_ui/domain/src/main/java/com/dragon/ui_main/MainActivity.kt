package com.dragon.ui_main


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.common_utils.ext.FragClazz
import com.dragon.ft_main.MainFragment
import com.dragon.search.SearchFragment
import com.dragon.ui_main.databinding.ActivityMainBinding
import com.kpstv.navigation.Destination
import com.kpstv.navigation.FragmentNavigator
import com.kpstv.navigation.canFinish

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) , FragmentNavigator.Transmitter {

    private val binding by viewBinding(ActivityMainBinding::bind)

    private lateinit var navigator: FragmentNavigator
    override fun getNavigator(): FragmentNavigator = navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //初始化
        navigator = FragmentNavigator.with(this,savedInstanceState)
            .initialize(binding.fragmentNav, Destination.of(MainFragment::class))
    }

    override fun onBackPressed() {
        if (navigator.canFinish()) super.onBackPressed()
    }
}
