package com.dragon.ft_main_home.views

import android.os.Bundle
import android.util.Log
import android.view.View
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.Success
import com.airbnb.mvrx.fragmentViewModel
import com.dragon.ft_main_home.viewmodle.ItemTabViewModel
import com.dragon.module_base.base.fragment.BaseEpoxyFragment
import com.dragon.module_base.base.fragment.BaseFragmentController
import com.dragon.module_base.base.fragment.simpleController

class ItemEpoxyFragment : BaseEpoxyFragment() {

    private val itemTabViewModel: ItemTabViewModel by fragmentViewModel()

    companion object {
        const val TYPE = "TYPE_FRAGMENT"
        fun getInstance(title: String): ItemEpoxyFragment {
            val fragment = ItemEpoxyFragment()
            fragment.arguments = Bundle().apply {
                putString(TYPE,title)
            }
            return fragment
        }
    }

    override fun requestRefresh(): Boolean {
        itemTabViewModel.initData()
        return true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTabViewModel.onEach {
            when(it.data){
                is Loading -> setIsRefreshing(0)
                is Success -> setIsRefreshing(1)
                is Fail -> setIsRefreshing(2)
                else -> {}
            }
        }
    }

    override fun epoxyController() = simpleController(itemTabViewModel) { state ->
        when(arguments?.getString(TYPE)){
            "测试" -> {
                state.data.invoke()?.forEachIndexed { index, topArticleBean ->
                    headerEpoxyView {
                        id("$index")
                        topArticle(topArticleBean)
                    }
                }
                Log.e("测试", "epoxyController: ${epoxyController.adapter.getModelById(0).hashCode()}", )
            }
        }
    }
}