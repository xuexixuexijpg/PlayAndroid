package com.dragon.ft_main_square

import com.airbnb.mvrx.fragmentViewModel
import com.dragon.ft_main_square.viewmodel.SquareViewModel
import com.dragon.module_base.base.fragment.BaseEpoxyFragment
import com.dragon.module_base.base.fragment.simpleController

class TestFragment : BaseEpoxyFragment() {

    private val squareViewModel:SquareViewModel by fragmentViewModel()

    override fun epoxyController() = simpleController {

    }
}