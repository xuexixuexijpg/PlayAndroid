package com.dragon.ft_main_home.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.dragon.ft_main_home.R
import com.dragon.ft_main_home.databinding.HomeEpoxyTopViewBinding
import com.dragon.ft_main_home.entity.TopArticleBean

@ModelView( autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class HeaderEpoxyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(HomeEpoxyTopViewBinding::bind)

    init {
        inflate(context, R.layout.home_epoxy_top_view,this)
    }

    @ModelProp
    fun setTopArticle(topArticleBean: TopArticleBean){
        binding.tvTopArticleAuthor.text = topArticleBean.author
        binding.homeTvTopArticleTitle.text = topArticleBean.title
    }
}