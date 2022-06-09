package com.dragon.ft_main_mine

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.ft_main_mine.databinding.FragmentFtMineBinding

class MineFragment : Fragment(R.layout.fragment_ft_mine){

    private val binding by viewBinding(FragmentFtMineBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding
    }
}