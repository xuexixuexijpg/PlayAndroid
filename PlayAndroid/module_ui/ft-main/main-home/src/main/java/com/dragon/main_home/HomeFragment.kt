package com.dragon.main_home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.main_home.databinding.FragmentMainHomeBinding
import com.kpstv.navigation.ValueFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : ValueFragment(R.layout.fragment_main_home) {

    private val binding by viewBinding(FragmentMainHomeBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.setOnClickListener {

        }
    }
}