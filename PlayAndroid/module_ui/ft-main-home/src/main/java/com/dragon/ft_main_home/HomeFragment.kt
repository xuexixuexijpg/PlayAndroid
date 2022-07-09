package com.dragon.ft_main_home

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.dragon.ft_main_home.adapter.ViewPagerAdapter
import com.dragon.ft_main_home.databinding.FragmentMainHomeBinding
import com.dragon.ft_main_home.viewmodle.HomeViewModel
import com.dragon.module_base.base.fragment.BaseFragment
import com.dylanc.longan.isLightStatusBar
import com.dylanc.longan.isStatusBarVisible
import com.dylanc.longan.statusBarColor
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_main_home) {

    @Inject
    lateinit var homeProvider: HomeProvider

//    private val homeViewModel: HomeViewModel by fragmentViewModel()

    private val binding by viewBinding(FragmentMainHomeBinding::bind)

    private lateinit var tabs:MutableList<String>

    private val navController get() = findNavController()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTabView()
        with(binding){
            viewpager.offscreenPageLimit = 1
            //这样使用主要是有个消息队列会泄露所以才这样做
            viewpager.adapter = ViewPagerAdapter(childFragmentManager,viewLifecycleOwner.lifecycle,tabs)
            viewpager.adapter?.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            TabLayoutMediator(lyTab,viewpager
            ) { tab, position ->
                tab.text = tabs[position]
            }.attach()
        }

    }

    private fun initTabView() {
        tabs = mutableListOf<String>().apply {
            add("测试")
//            add("测试2")
        }
        binding.lyTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
    }

}