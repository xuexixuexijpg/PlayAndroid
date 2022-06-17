package com.dragon.ft_main_mine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.mvrx.*
import com.dragon.ft_main_mine.databinding.FragmentFtMineBinding
import com.dragon.ft_main_mine.itemView.ItemViews
import com.dragon.module_base.base.fragment.BaseFragment
import com.dragon.module_base.base.fragment.BaseFragmentController
import com.dragon.module_base.base.fragment.simpleController
import com.drake.brv.BindingAdapter
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.dylanc.longan.launchAndCollectIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MineFragment : BaseFragment(R.layout.fragment_ft_mine), MavericksView {

    @Inject
    lateinit var homeProvider: MineProvider

    private val binding by viewBinding(FragmentFtMineBinding::bind)

    private val mineViewModel: MineViewModel by fragmentViewModel()

    private val testViewModel: TestViewModel by viewModels()

    private lateinit var adapter: BindingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = binding.recyclerView.linear().setup {
            //解决重建时使用MaveRicks无法保存滚动位置的情况
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            addType<MineData>(R.layout.item_mine_data)
            onBind {
                findView<TextView>(R.id.textView2).text = getModel<MineData>().title
            }
            R.id.textView2.onClick {
                homeProvider.navigateToPage(id = R.id.action_main_to_search)
            }
        }


        //跟方法一样 发生在RESUMED阶段
//        mineViewModel.onEach(MineDataList::resultData) {
//            Log.e("测试", "invalidate: ${lifecycle.currentState} == ${it.hashCode()}")
//            adapter.setDifferModels(it)
//        }

        //生命周发生在 RESUMED Async值 试了多次之后列表滚动状态还是会丢失
//        mineViewModel.onAsync(MineDataList::data) {
//            adapter.setDifferModels(it)
//        }

    }


    //测试这里 invalidate首次创建时会先started再resume
    override fun invalidate() {
        withState(mineViewModel) { state ->
            Log.e("测试", "invalidate: ${lifecycle.currentState}")
            when(state.data){
                //使用Async包装了一层 直接invoke()可直接取出数据
                is Success -> {
                    adapter.setDifferModels(state.data.invoke())
                }
                is Loading -> {}
                is Fail -> {}
                else -> {}
            }
        }
    }

}