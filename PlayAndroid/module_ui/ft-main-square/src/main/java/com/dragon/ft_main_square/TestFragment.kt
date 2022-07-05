package com.dragon.ft_main_square

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import com.airbnb.epoxy.stickyheader.StickyHeaderLinearLayoutManager
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.dragon.ft_main_square.viewmodel.SquareViewModel
import com.dragon.ft_main_square.views.squareHeaderView
import com.dragon.module_base.base.fragment.BaseEpoxyFragment
import com.dragon.module_base.base.fragment.simpleController

class TestFragment : BaseEpoxyFragment() {

    private val squareViewModel: SquareViewModel by fragmentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (squareViewModel.getData().textData.isEmpty()) {
            squareViewModel.setData(mutableListOf<String>().apply {
                for (i in 1..100) {
                    add(i.toString())
                }
            })
        }
    }

    override fun setCanRefresh(): Pair<Boolean, Boolean> {
        return Pair(false,false)
    }

//    override fun isSticky(): StickyHeaderLinearLayoutManager {
//        return StickyHeaderLinearLayoutManager(requireContext())
//    }

    override fun epoxyController() = simpleController(squareViewModel) { state ->
        Log.e("测试生命周期", "epoxyController:广场 ${lifecycle.currentState}", )
        for (s in state.textData) {
            squareHeaderView {
                id(s)
                text(s)
            }
        }
    }
}