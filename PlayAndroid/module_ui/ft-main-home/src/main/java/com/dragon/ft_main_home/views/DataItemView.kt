package com.dragon.ft_main_home.views

import android.content.Context
import android.system.Os.bind
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.dragon.ft_main_home.R
import com.dragon.ft_main_home.databinding.HomeViewDataItemBinding

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class DataItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(HomeViewDataItemBinding::bind)

    init {
        inflate(context, R.layout.home_view_data_item,this)
    }

    @TextProp
    fun setData(data:CharSequence){
        binding.textView2.text = data
    }
}