package com.dragon.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.search.databinding.FragmentFtSearchBinding
import com.kpstv.navigation.FragmentNavigator
import com.kpstv.navigation.ValueFragment
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 搜索页面
 */
@AndroidEntryPoint
class SearchFragment:ValueFragment(R.layout.fragment_ft_search) {
    private val binding by viewBinding(FragmentFtSearchBinding::bind)

    @Inject
    lateinit var searchProvider: SearchProvider

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {

        }
    }
}