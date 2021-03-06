package com.dragon.module_base.base.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.mvrx.MavericksView
import com.dragon.module_base.R
import com.dragon.module_base.base.activity.BaseRouteActivity
import com.dragon.module_base.base.callback.BackPressedCallback
import com.dragon.module_base.base.callback.BackPressedOwner
import com.dragon.module_base.databinding.FragmentEpoxyBaseBinding
import com.dragon.module_base.event.LoadResult
import com.dragon.module_base.service.navigate.BaseArgs


/**
 * 基础fragment 用的EpoxyView
 */
abstract class BaseOriginEpoxyFragment : Fragment(R.layout.fragment_epoxy_base), MavericksView {

    companion object {
        fun createArgKey(args: BaseArgs): String = createArgKey(args::class.qualifiedName!!)
        fun createArgKey(identifier: String): String = "$VALUE_ARGUMENT:$identifier"

        private const val VALUE_ARGUMENT = "com.dragon.BaseArgs"
    }

    //控制器
    protected val epoxyController by lazy { epoxyController() }

    private val binding by viewBinding(FragmentEpoxyBaseBinding::bind)


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseRouteActivity) {
            //注册返回键事件
            context.backPressedDispatcherAM.addCallback(this, object : BackPressedCallback() {
                override fun handleOnBackPressed(owner: BackPressedOwner): Boolean {
                    return this@BaseOriginEpoxyFragment.handleOnBackPressed(owner)
                }
            })
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        savedInstanceState?.let {
        epoxyController.onRestoreInstanceState(savedInstanceState)
//        }
        //删除重复的id项

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        epoxyController.setFilterDuplicates(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    /**
     * 设置是否可以下拉刷新 默认false
     */
    protected open fun setCanRefresh(): Boolean {
        return true
    }

    /**
     * 请求刷新数据处
     */
    protected open fun requestRefresh(){}

    /**
     * 设置刷新标志
     *  0 加载中
     *  1 成功
     *  2 失败
     */
    fun setIsRefreshing(isRefresh: Int) {
        with(binding) {
            when (isRefresh) {
                LoadResult.LOADING.state -> lyRefresh.isRefreshing = true
                LoadResult.SUCCESS.state -> lyRefresh.isRefreshing = false
                LoadResult.FAIL.state -> {
                    lyRefresh.isRefreshing = false
                    Log.e("测试", "setIsRefreshing: 加载失败了")
                }
                else -> {}
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    protected open fun isSticky(): LinearLayoutManager? {
        return null
    }

    //可在此处做一些操作 如全局的viewModel监听
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isSticky() != null) {
            binding.recycleView.layoutManager = isSticky()
        }else{
            binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        }
        binding.lyRefresh.isEnabled = setCanRefresh()
        if (setCanRefresh()) {
            binding.lyRefresh.setOnRefreshListener {
                requestRefresh()
            }
        }
        binding.recycleView.setController(epoxyController)
        binding.recycleView.adapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }


    /**
     * viewModel数据更新处
     */
    override fun invalidate() {
        binding.recycleView.requestModelBuild()
//        epoxyController.requestModelBuild()
    }


    abstract fun epoxyController(): BaseFragmentController

    override fun onDestroyView() {
        //TODO 解决view回收导致的内存泄漏问题 暂时，可能还有其他的写法
        //https://github.com/airbnb/epoxy/wiki/Avoiding-Memory-Leaks
        epoxyController.cancelPendingModelBuild()
        super.onDestroyView()
    }

    /**
     * 处理返回键事件
     */
    protected open fun handleOnBackPressed(owner: BackPressedOwner): Boolean {
        //默认不消耗返回键事件
        //可以重写此方法在Fragment中处理返回键事件逻辑
        return false
    }
}