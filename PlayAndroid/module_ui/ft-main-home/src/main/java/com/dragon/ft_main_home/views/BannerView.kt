package com.dragon.ft_main_home.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.dragon.ft_main_home.R
import com.dragon.ft_main_home.adapter.ImageAdapter
import com.dragon.ft_main_home.databinding.HomeBannerBinding
import com.dragon.ft_main_home.databinding.HomeEpoxyBannerBinding
import com.dragon.ft_main_home.entity.BannerBean
import com.dragon.widgets.viewgroup.NestedScrollableHost
import com.youth.banner.config.BannerConfig
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.util.BannerUtils

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT, saveViewState = true)
class BannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(HomeEpoxyBannerBinding::bind)

    init {
        inflate(context, R.layout.home_banner, this)
        binding.banner.setAdapter(ImageAdapter(null)).apply {
            //设置圆点指示器
            indicator = CircleIndicator(context)
            //设置被选中颜色
            setIndicatorSelectedColorRes(android.R.color.holo_blue_bright)
            //设置位置
            setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
            //设置边距
            setIndicatorMargins(
                IndicatorConfig.Margins(
                    0,
                    0,
                    BannerConfig.INDICATOR_MARGIN,
                    BannerUtils.dp2px(15f)
                )
            )
        }
    }

    @ModelProp(ModelProp.Option.DoNotHash)
    fun addBannerLifeCycle(lifecycleOwner: LifecycleOwner) {
        binding.banner.addBannerLifecycleObserver(lifecycleOwner)
    }

    @ModelProp
    fun setImageData(images:MutableList<BannerBean>?) {
        binding.banner.setDatas(images)
    }
}