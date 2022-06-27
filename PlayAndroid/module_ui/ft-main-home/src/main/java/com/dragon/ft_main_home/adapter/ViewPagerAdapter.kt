package com.dragon.ft_main_home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dragon.ft_main_home.views.ItemEpoxyFragment
import com.dragon.ft_main_home.views.ItemTabFragment


/**
 * FragmentStateAdapter
 */
class ViewPagerAdapter(fragment: FragmentManager,lifecycle: Lifecycle, private val list: MutableList<String>) : FragmentStateAdapter(fragment,lifecycle) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ItemEpoxyFragment.getInstance(list[0])
            1 -> ItemEpoxyFragment.getInstance(list[1])
            else -> ItemEpoxyFragment.getInstance(list[0])
        }
    }
}