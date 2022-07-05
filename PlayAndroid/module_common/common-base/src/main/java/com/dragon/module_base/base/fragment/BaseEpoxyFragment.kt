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
import com.airbnb.epoxy.stickyheader.StickyHeaderLinearLayoutManager
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksView
import com.dragon.module_base.R
import com.dragon.module_base.base.activity.BaseActivity
import com.dragon.module_base.base.callback.BackPressedCallback
import com.dragon.module_base.base.callback.BackPressedOwner
import com.dragon.module_base.databinding.FragmentBaseBinding
import com.dragon.module_base.event.LoadResult
import com.dragon.module_base.service.navigate.BaseArgs


/**
 * 基础fragment 用的MyRecyclerView
 */
abstract class BaseEpoxyFragment : Fragment(R.layout.fragment_base), MavericksView {

    companion object {
        fun createArgKey(args: BaseArgs): String = createArgKey(args::class.qualifiedName!!)
        fun createArgKey(identifier: String): String = "$VALUE_ARGUMENT:$identifier"

        private const val VALUE_ARGUMENT = "com.dragon.BaseArgs"
    }

    //控制器
    protected val epoxyController by lazy { epoxyController() }

    private val binding by viewBinding(FragmentBaseBinding::bind)

    //是否正在下拉刷新上拉加载更多
    private var isRefreshing = false

    private var isLoadingNextPage = false
    private var isNoMoreData = false


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            //注册返回键事件
            context.backPressedDispatcherAM.addCallback(this, object : BackPressedCallback() {
                override fun handleOnBackPressed(owner: BackPressedOwner): Boolean {
                    return this@BaseEpoxyFragment.handleOnBackPressed(owner)
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
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            epoxyController.setFilterDuplicates(true)
        }
    }

    /**
     * 设置是否可以下拉刷新，上拉加载更多 默认true,true
     */
    protected open fun setCanRefresh(): Pair<Boolean, Boolean> {
        return Pair(true, true)
    }

    /**
     * 请求刷新数据处
     */
    protected open fun requestRefresh() {}

    /**
     * 加载更多数据
     */
    protected open fun requestLoadMore() {}

    /**
     * 设置刷新 或 加载更多标志  freshType默认为0为设置刷新头状态 其他为加载更多
     *  0 加载中
     *  1 成功
     *  2 失败
     */
    fun setIsRefreshing(isRefresh: Int, freshType: Int = 0) {
        with(binding) {
            if (freshType == 0)
                when (isRefresh) {
                    LoadResult.LOADING.state -> {
                        lyRefresh.isRefreshing = true
                        isRefreshing = true
                    }
                    LoadResult.SUCCESS.state -> {
                        lyRefresh.isRefreshing = false
                        isRefreshing = false
                    }
                    LoadResult.FAIL.state -> {
                        lyRefresh.isRefreshing = false
                        isRefreshing = false
                        Log.e("测试", "setIsRefreshing: 刷新失败了")
                    }
                    else -> {}
                }
            else
                //加载状态
                when (isRefresh) {
                    LoadResult.LOADING.state -> {
                        isLoadingNextPage = true
                        isNoMoreData = false
                    }
                    LoadResult.SUCCESS.state -> {
                        isLoadingNextPage = false
                        isNoMoreData = false
                    }
                    LoadResult.FAIL.state -> {
                        isLoadingNextPage = false
                        isNoMoreData = false
                    }
                    LoadResult.NO_MORE_DATA.state -> {
                        isLoadingNextPage = false
                        isNoMoreData = true
                    }
                    else -> {}
                }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    /**
     * 默认LinearLayoutManager
     */
    protected open fun isSticky(): LinearLayoutManager? {
        return null
    }

    //可在此处做一些操作 如全局的viewModel监听
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isSticky() != null) {
            binding.recycleView.layoutManager = isSticky()
        } else {
            binding.recycleView.layoutManager = LinearLayoutManager(requireContext())
        }
        binding.lyRefresh.isEnabled = setCanRefresh().first
        if (setCanRefresh().first) {
            binding.lyRefresh.setOnRefreshListener {
                requestRefresh()
            }
        }
        binding.recycleView.setController(epoxyController)
        binding.recycleView.adapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        if (setCanRefresh().second) {
            binding.recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val lm = recyclerView.layoutManager as? LinearLayoutManager ?: return
                    val lastVisibleItem = lm.findLastVisibleItemPosition()
                    val totalItemCount = lm.itemCount

                    if (lastVisibleItem == totalItemCount - 1 && !isLoadingNextPage && !isRefreshing && !isNoMoreData) {
                        if (totalItemCount > 0) {
                            // 在前面addLoadItem后，itemCount已经变化
                            // 增加一层判断，确保用户是滑到了正在加载的地方，才加载更多
                            val findLastVisibleItemPosition = lm.findLastVisibleItemPosition()
                            if (findLastVisibleItemPosition == lm.itemCount - 1) {
                                isLoadingNextPage = true
                                requestLoadMore()
                            }
                        }
                    }
                }
            })
        }
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