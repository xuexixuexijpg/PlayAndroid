package com.dragon.module_base.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.MavericksView
import com.dragon.module_base.R
import com.dragon.module_base.databinding.FragmentBaseBinding
import com.kpstv.navigation.ValueFragment

/**
 * 基础fragment
 */
abstract class BaseFragment : ValueFragment(R.layout.fragment_base),MavericksView {

    //控制器
    protected val epoxyController by lazy { epoxyController() }

    private val binding by viewBinding(FragmentBaseBinding::bind)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
    }

    //可在此处做一些操作 如全局的viewModel监听
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            recycleView.setController(epoxyController)
        }
    }

    /**
     * viewModel数据更新处
     */
    override fun invalidate() {
        //建立模型
        binding.recycleView.requestModelBuild()
    }

    abstract fun epoxyController(): BaseFragmentController
}