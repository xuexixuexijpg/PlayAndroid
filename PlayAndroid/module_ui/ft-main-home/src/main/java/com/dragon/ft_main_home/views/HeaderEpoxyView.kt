package com.dragon.ft_main_home.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.PackageModelViewConfig
import com.airbnb.epoxy.TextProp
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

    @TextProp
    fun setAuthor(author: CharSequence?){
        binding.tvTopArticleAuthor.text = author
    }

    @TextProp
    fun setTitle(title: CharSequence?){
        binding.homeTvTopArticleTitle.text = title
    }

}