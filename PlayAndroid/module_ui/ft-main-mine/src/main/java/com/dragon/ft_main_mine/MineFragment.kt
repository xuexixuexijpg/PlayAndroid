package com.dragon.ft_main_mine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dragon.ft_main_mine.databinding.FragmentFtMineBinding
import com.drake.brv.utils.bindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MineFragment : Fragment(R.layout.fragment_ft_mine){

    @Inject
    lateinit var homeProvider: MineProvider

    private val binding by viewBinding(FragmentFtMineBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.linear().setup {
            addType<MineData>(R.layout.item_mine_data)
            onBind {
                findView<TextView>(R.id.textView2).text = getModel<MineData>().title
            }
            R.id.textView2.onClick {
                homeProvider.navigateToPage(id = R.id.action_main_to_search)
            }
        }.models = getData()

        //每次创建都是新的一个adapter
        Log.e("onCreateView", "BRV: ${binding.recyclerView.bindingAdapter}", )
    }

    private fun getData(): MutableList<MineData> {
        return mutableListOf<MineData>().apply {
           for (i in 1..100){
               add(MineData(i.toString()))
           }
        }
    }
}