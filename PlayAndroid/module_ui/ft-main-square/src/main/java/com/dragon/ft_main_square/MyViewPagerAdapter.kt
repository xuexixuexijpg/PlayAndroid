package com.dragon.ft_main_square

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dragon.module_base.base.fragment.BaseFragment

class MyViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TestFragment()
            1 -> TestFragment()
            else -> TestFragment()
        }
    }
}