package com.dragon.ft_main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.ft_main.databinding.FragmentFtMainBinding
import com.kpstv.navigation.FragmentNavigator
import com.kpstv.navigation.ValueFragment
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * 主页
 */
@AndroidEntryPoint
class MainFragment : ValueFragment(R.layout.fragment_ft_main) {

    @Inject
    lateinit var mainProvider: MainProvider

    private val binding by viewBinding(FragmentFtMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}