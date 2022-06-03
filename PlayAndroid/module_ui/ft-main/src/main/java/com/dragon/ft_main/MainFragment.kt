package com.dragon.ft_main

import android.app.Activity
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowLayoutInfo
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.common_utils.mmkvutil.MMKVOwner
import com.dragon.common_utils.mmkvutil.mmkvParcelable
import com.dragon.ft_main.databinding.FragmentFtMainBinding
import com.dragon.common_data.mmkv.LayoutChangeInfo
import com.dragon.ft_main.di.mainmodule.MainProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * 主页
 */
@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_ft_main), MMKVOwner {

    //直接 findNavController()拿到的是父的控制器
    private lateinit var navController: NavController

    //屏幕信息
    private var layoutInfo by mmkvParcelable<LayoutChangeInfo>()

//    private val navViewModel by navGraphViewModels<NavViewModel>(R.id.main_graph,navController)

    //配置信息
    private lateinit var configuration: Configuration

    @Inject
    lateinit var mainProvider: MainProvider

    private val binding by viewBinding(FragmentFtMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //https://github.com/android/user-interface-samples/tree/main/WindowManager
        configuration = resources.configuration
        context?.let {
            lifecycleScope.launch(Dispatchers.Main) {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    //照理说不应该会报activity的内存泄露
                    val activity = WeakReference<Activity>(requireActivity())
                    activity.get()?.let { ac ->
                        WindowInfoTracker.getOrCreate(it)
                            .windowLayoutInfo(ac)
                            .collect { newLayoutInfo ->
                                //根据窗口信息改变状态
                                saveLayoutInfo(newLayoutInfo)
                                layoutInfo?.let { info ->
                                    val type = info.screenWidth
                                    if (type == 0) {
                                        initBottomNav()
                                    } else {
                                        initRailNav()
                                    }
                                }
                            }
                    }
                }
            }
        }

        val hostFragment = childFragmentManager.findFragmentById(R.id.ft_main_container)
        if (hostFragment != null) {
            //不先在布局中
            navController = hostFragment.findNavController()
        }

        binding.bottomNav.setupWithNavController(navController)

        binding.railNav.setupWithNavController(navController)


//        navViewModel.selectItem.launchAndCollectIn(viewLifecycleOwner){
//            if (it in arrayListOf(R.id.homeFragment,R.id.mineFragment,R.id.squareFragment)){
//                binding.bottomNav.selectedItemId = it
//            }
//        }
    }


    /**
     * 目前的做法是只导航到本地
     */
    fun bottomItemSelect(@IdRes id:Int){
        binding.bottomNav.selectedItemId = id
    }


    private fun initBottomNav() {
        //底部导航结合
        binding.bottomNav.visibility = View.VISIBLE
        binding.railNav.visibility = View.GONE
    }

    private fun initRailNav() {
        //侧边导航
        binding.bottomNav.visibility = View.GONE
        binding.railNav.visibility = View.VISIBLE
    }

    companion object {
        const val TAG = "MainFragment"
    }


    /**
     * 拿到窗口信息并更新UI界面
     */
    private fun saveLayoutInfo(newLayoutInfo: WindowLayoutInfo) {
        //横屏状态
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            kv.remove(::LayoutChangeInfo.name)
        }
        //w小于600
        if (configuration.screenWidthDp < 600) {
            layoutInfo = LayoutChangeInfo(0)
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
            layoutInfo = if (hasRestView && configuration.screenWidthDp < 1024) {
                LayoutChangeInfo(1)
            } else {
                LayoutChangeInfo(2)
            }
        }
    }
}