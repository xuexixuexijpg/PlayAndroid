package com.dragon.module_base.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.MavericksView
import com.dragon.module_base.R
import com.dragon.module_base.databinding.FragmentBaseBinding
import com.dragon.module_base.service.navigate.BaseArgs


/**
 * 基础fragment
 */
abstract class BaseFragment : Fragment(R.layout.fragment_base),MavericksView {

    companion object {
        fun createArgKey(args: BaseArgs): String = createArgKey(args::class.qualifiedName!!)
        fun createArgKey(identifier: String): String = "$VALUE_ARGUMENT:$identifier"

        private const val VALUE_ARGUMENT = "com.dragon.BaseArgs"
    }

    //控制器
    protected val epoxyController by lazy { epoxyController() }

    private val binding by viewBinding(FragmentBaseBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            epoxyController.onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    //可在此处做一些操作 如全局的viewModel监听
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        binding.recycleView.setController(epoxyController)
    }

    /**
     * viewModel数据更新处
     */
    override fun invalidate() {
        //建立模型
        binding.recycleView.setControllerAndBuildModels(epoxyController)
        //此处会内存泄露 recycleView会释放不了
//        binding.recycleView.requestModelBuild()
    }

    abstract fun epoxyController(): BaseFragmentController

    override fun onDestroyView() {
        binding.recycleView.removeAllViews()
        super.onDestroyView()
    }
}