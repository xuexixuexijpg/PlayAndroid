package com.dragon.ft_main_mine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.dragon.ft_main_mine.databinding.FragmentFtMineBinding
import com.dragon.module_base.base.fragment.BaseFragment
import com.drake.brv.BindingAdapter
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MineFragment : BaseFragment(R.layout.fragment_ft_mine),MavericksView{

    @Inject
    lateinit var homeProvider: MineProvider

    private val binding by viewBinding(FragmentFtMineBinding::bind)

    private val mineViewModel:MineViewModel by fragmentViewModel()

    private lateinit var adapter: BindingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter  = binding.recyclerView.linear().setup {
            addType<MineData>(R.layout.item_mine_data)
            onBind {
                findView<TextView>(R.id.textView2).text = getModel<MineData>().title
            }
            R.id.textView2.onClick {
                homeProvider.navigateToPage(id = R.id.action_main_to_search)
            }
        }
        //每次创建都是新的一个adapter
    }

    override fun invalidate() {
        withState(mineViewModel){
            adapter.models = it.resultData
            Log.e("测试", "invalidate: ", )
        }
    }

}