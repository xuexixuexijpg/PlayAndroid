package com.dragon.ft_main_square

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.ft_main_square.databinding.FragmentFtSquareBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.textview.MaterialTextView

class SquareFragment : Fragment(R.layout.fragment_ft_square) {

    val tabs = mutableListOf<String>("测试1", "测试2")

    private val binding by viewBinding(FragmentFtSquareBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            tabLayout.isTabIndicatorFullWidth = false
            tabLayout.setUnboundedRipple(false)
            tabLayout.setTabTextColors(R.color.cardview_shadow_end_color, R.color.holo_red_dark)

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.customView?.let {
                        tab.view.isLongClickable = false;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                            tab.view.tooltipText = ""
                        val tv = it.findViewById<TextView>(android.R.id.text1)
                        tv.setTextColor(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.holo_red_dark
                            )
                        )
                        tv.typeface = Typeface.DEFAULT_BOLD
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tab?.customView?.let {
                        val tv = it.findViewById<TextView>(android.R.id.text1)
                        tv.typeface = Typeface.DEFAULT
                    }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })

            viewPager.adapter = MyViewPagerAdapter(this@SquareFragment)
            viewPager.offscreenPageLimit = 1
            TabLayoutMediator(
                tabLayout,
                viewPager,
            ) { tab, position ->
                tab.text = tabs[position]
                tab.setCustomView(R.layout.item_tab_customview)
            }.attach()
        }
    }


    companion object {
        const val TAG = "SquareFragment"
    }
}