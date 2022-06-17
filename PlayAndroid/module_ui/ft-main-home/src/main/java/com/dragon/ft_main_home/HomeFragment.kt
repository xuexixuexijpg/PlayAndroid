package com.dragon.ft_main_home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.dragon.ft_main_home.viewmodle.HomeViewModel
import com.dragon.module_base.base.fragment.BaseFragment
import com.dragon.module_base.databinding.FragmentBaseBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_main_home),MavericksView {

    @Inject
    lateinit var homeProvider: HomeProvider

    private val homeViewModel: HomeViewModel by fragmentViewModel()

    private val binding by viewBinding(FragmentBaseBinding::bind)

    private val navController get() = findNavController()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.setData(mutableListOf<String>().apply {
            for (i in 1..100){
                add(i.toString())
            }
        })
    }

    override fun invalidate() {

    }


}