package com.dragon.ft_main_home.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.dragon.ft_main_home.R
import com.dragon.ft_main_home.adapter.BannerImageAdapter
import com.dragon.ft_main_home.databinding.HomeBannerPageViewBinding
import com.dragon.ft_main_home.entity.BannerBean
import com.drake.net.NetConfig.init
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.constants.IndicatorGravity
import com.zhpan.indicator.enums.IndicatorSlideMode

/**
 * banner  必须加saveViewState记住滚动位置
 */
@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT, saveViewState = true)
class BannerPageView  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(HomeBannerPageViewBinding::bind)

    init {
        inflate(context, R.layout.home_banner_page_view,this)
        binding.bannerView.apply {
            setIndicatorSlideMode(IndicatorSlideMode.SCALE)
            setIndicatorSliderColor(
                ContextCompat.getColor(context, R.color.home_top_article_author),
                ContextCompat.getColor(context,android.R.color.holo_blue_bright)
            )
            setIndicatorGravity(IndicatorGravity.END)
            setIndicatorSliderRadius(3)
            setAdapter(BannerImageAdapter())
            setPageMargin(20)
            setRevealWidth(-10)
        }.create()
    }

    @ModelProp(ModelProp.Option.DoNotHash)
    fun addBannerLifeCycle(lifecycleOwner: Lifecycle) {
        binding.bannerView.setLifecycleRegistry(lifecycleOwner)
    }

    @ModelProp
    fun setImageData(images:MutableList<BannerBean>?) {
        binding.bannerView.refreshData(images)
    }
}