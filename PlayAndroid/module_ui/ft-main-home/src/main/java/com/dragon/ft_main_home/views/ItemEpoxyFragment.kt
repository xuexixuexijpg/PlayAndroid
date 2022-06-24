package com.dragon.ft_main_home.views

import android.os.Bundle
import com.airbnb.mvrx.fragmentViewModel
import com.dragon.ft_main_home.viewmodle.ItemTabViewModel
import com.dragon.module_base.base.fragment.BaseEpoxyFragment
import com.dragon.module_base.base.fragment.BaseFragmentController
import com.dragon.module_base.base.fragment.simpleController

class ItemEpoxyFragment : BaseEpoxyFragment() {

    private val itemTabViewModel: ItemTabViewModel by fragmentViewModel()

    companion object {
        const val TYPE = "TYPE_FRAGMENT"
        fun getInstance(title: String): ItemTabFragment {
            val fragment = ItemTabFragment()
            fragment.arguments = Bundle().apply {
                putString(TYPE,title)
            }
            return fragment
        }
    }

    override fun epoxyController() = simpleController(itemTabViewModel) { state ->
        when(arguments?.getString(ItemTabFragment.TYPE)){
            "测试" -> {

            }
        }
    }
}