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

    private val testViewModel:TestViewModel by viewModels()

    private lateinit var adapter: BindingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         adapter = binding.recyclerView.linear().setup {
            addType<MineData>(R.layout.item_mine_data)
            onBind {
                findView<TextView>(R.id.textView2).text = getModel<MineData>().title
            }
            R.id.textView2.onClick {
                homeProvider.navigateToPage(id = R.id.action_main_to_search)
            }
        }
        if (testViewModel.testData.value.isNullOrEmpty()){
            testViewModel.setData()
        }
        testViewModel.testData.launchAndCollectIn(viewLifecycleOwner){
            adapter.setDifferModels(it)
        }

        //另一种写法 可直接取属性和设置模式 deliveryMode = uniqueOnly() 一次性
        //由于只发送一次独一无二的数据后面页面重建就没有数据了 另一个是，默认的相当于viewModel了
        //不过viewModel也有扩展函数distinck啥来着的api

        //跟invalidate()方法一样
//        mineViewModel.onEach(MineDataList::resultData) {
//        Log.e("测试", "invalidate: ${lifecycle.currentState}")
//            adapter.setDifferModels(it)
//        }

        //生命周发生在 STARTED Async值 试了多次之后列表滚动状态还是会丢失
//        mineViewModel.onAsync(MineDataList::data) {
//            adapter.setDifferModels(it)
//        }

    }

    private fun getData(): List<Any?> {
        return mutableListOf<MineData>().apply {
            for (i in 1..100) {
                add(MineData(i.toString()))
            }
        }
    }


    //测试这里 invalidate首次创建时会先started再resume 之后只有started
    override fun invalidate() {
//        withState(mineViewModel) { state ->
//            Log.e("测试", "invalidate: ${lifecycle.currentState}")
//            when(state.data){
//                //使用Async包装了一层 直接invoke()可直接取出数据
//                is Success -> {
//                    adapter.setDifferModels(state.data.invoke())
//                }
//                is Loading -> {}
//                is Fail -> {}
//                else -> {}
//            }
//        }
    }

}