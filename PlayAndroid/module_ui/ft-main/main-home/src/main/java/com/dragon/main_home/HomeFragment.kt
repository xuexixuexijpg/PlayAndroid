package com.dragon.main_home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.main_home.databinding.FragmentMainHomeBinding
import com.dragon.module_base.base.fragment.BaseFragment
import com.dragon.module_base.base.fragment.BaseFragmentController
import com.dragon.module_base.base.fragment.simpleController
import com.kpstv.navigation.ValueFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun epoxyController() = simpleController {

    }
}