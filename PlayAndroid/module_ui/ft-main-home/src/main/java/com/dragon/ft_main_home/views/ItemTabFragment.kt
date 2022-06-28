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
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.dragon.ft_main_home.R
import com.dragon.ft_main_home.adapter.ImageAdapter
import com.dragon.ft_main_home.databinding.FragmentItemTabBinding
import com.dragon.ft_main_home.entity.BannerBean
import com.dragon.ft_main_home.entity.TopArticleBean
import com.dragon.ft_main_home.viewmodle.HomeArticle
import com.dragon.ft_main_home.viewmodle.ItemTabViewModel
import com.drake.brv.BindingAdapter
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.drake.net.NetConfig.init
import com.youth.banner.Banner
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.config.BannerConfig
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.util.BannerUtils
import java.util.Collections.addAll

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
        //多模型adapter
        adapter = binding.rvList.linear().setup {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
            addType<TopArticleBean>(R.layout.home_top_article_item)
            addType<HomeArticle>(R.layout.home_banner)
            onCreate { type ->
                if (type == R.layout.home_banner)
                    findView<Banner<BannerBean, ImageAdapter>>(R.id.banner).setAdapter(ImageAdapter(null))
                        .addBannerLifecycleObserver(viewLifecycleOwner).apply {
                            //设置圆点指示器
                            indicator = CircleIndicator(context)
                            //设置被选中颜色
                            setIndicatorSelectedColorRes(android.R.color.holo_blue_bright)
                            //设置位置
                            setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                            //设置边距
                            setIndicatorMargins(IndicatorConfig.Margins(0, 0, BannerConfig.INDICATOR_MARGIN, BannerUtils.dp2px(15f)))
                        }
            }
            onBind {
                when(itemViewType){
                    R.layout.home_top_article_item -> {
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
                    R.layout.home_banner -> {
                        getModel<HomeArticle>().run {
                            (findView<Banner<BannerBean, ImageAdapter>>(R.id.banner).adapter as ImageAdapter)
                                .setDatas(this.bannerData.invoke())
                        }
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

    override fun invalidate() = withState(itemTabViewModel){ data ->
        if (data.topArticleData is Loading || data.bannerData is Loading)return@withState
        val info = mutableListOf<Any>()
        data.topArticleData.invoke()?.let { info.addAll(it) }
        data.let { info.add(data) }
        adapter.setDifferModels(info)
    }

    private fun initAdapter(bindingAdapter: BindingAdapter) {

    }
}
