package com.dragon.ft_main_home.views

import android.content.Context
import android.system.Os.bind
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.dragon.ft_main_home.R
import com.dragon.ft_main_home.databinding.HomeViewHeaderBinding


/**
 * 建立模型view
 */
@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class HeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(HomeViewHeaderBinding::bind)

    init {
        inflate(context, R.layout.home_view_header,this)
    }

    @CallbackProp
    fun onAvatarClick(listener: OnClickListener?){
        binding.imgAvatar.setOnClickListener(listener)
    }

    @CallbackProp
    fun onSearchLayoutClick(listener: OnClickListener?){
        binding.layoutSearchArea.setOnClickListener(listener)
    }

    @TextProp
    fun setHintText(title: CharSequence?){
        binding.tvSearch.text = title
    }
}