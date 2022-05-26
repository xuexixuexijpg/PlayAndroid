package com.dragon.ui_main


import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.Consumer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.window.layout.FoldingFeature
import androidx.window.layout.FoldingFeature.State.Companion.FLAT
import androidx.window.layout.FoldingFeature.State.Companion.HALF_OPENED
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.ft_main.MainFragment
import com.dragon.ui_main.databinding.ActivityMainBinding
import com.kpstv.navigation.Destination
import com.kpstv.navigation.FragmentNavigator
import com.kpstv.navigation.canFinish
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        //https://github.com/android/user-interface-samples/tree/main/WindowManager
        lifecycleScope.launch(Dispatchers.Main){
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                WindowInfoTracker.getOrCreate(this@MainActivity)
                    .windowLayoutInfo(this@MainActivity)
                    .collect { newLayoutInfo ->
                        //根据窗口信息改变状态 由于MainFragment无法持有activity的内容
                        updateCurrentState(newLayoutInfo)
                    }
            }
        }
    }

    private fun updateCurrentState(newLayoutInfo: WindowLayoutInfo) {
        val mainFragment = getNavigator().getCurrentFragment()
        if (mainFragment != null && mainFragment is MainFragment){
            mainFragment.setLayoutInfo(newLayoutInfo)
        }
    }

    override fun onBackPressed() {
        if (navigator.canFinish()) super.onBackPressed()
    }
}
