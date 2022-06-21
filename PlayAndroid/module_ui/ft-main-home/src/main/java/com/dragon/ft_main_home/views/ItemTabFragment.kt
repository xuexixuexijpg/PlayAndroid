package com.dragon.ft_main_home.views

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.dragon.ft_main_home.R
import com.dragon.ft_main_home.databinding.FragmentItemTabBinding
import com.dragon.ft_main_home.model.TopArticleBean
import com.dragon.ft_main_home.viewmodle.ItemTabViewModel
import com.dragon.module_base.base.fragment.BaseFragment
import com.drake.brv.BindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup

class ItemTabFragment :
    Fragment(R.layout.fragment_item_tab), MavericksView {

    private lateinit var adapter: BindingAdapter
    private val binding by viewBinding(FragmentItemTabBinding::bind)

    private val itemTabViewModel:ItemTabViewModel by fragmentViewModel()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = binding.rvList.linear().setup {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            addType<TopArticleBean>(R.layout.home_top_article_item)
            onBind {
                getModel<TopArticleBean>().run {
                    //设置标题
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        findView<TextView>(R.id.home_tv_top_article_title).text =
                            Html.fromHtml(title, Html.FROM_HTML_MODE_LEGACY)
                    } else {
                        findView<TextView>(R.id.home_tv_top_article_title).text = Html.fromHtml(title)
                    }
                    if (author.isNotEmpty()){
                        findView<TextView>(R.id.tv_top_article_author).text = author
                    }else{
                        findView<TextView>(R.id.tv_top_article_author).text = shareUser
                    }
                }
            }
        }
        val type = arguments?.getString(TYPE)
        if (type.isNullOrEmpty()){
            when (type) {

            }
        }
    }

    override fun invalidate() = withState(itemTabViewModel){
        adapter.models = it.data.invoke()
    }
}