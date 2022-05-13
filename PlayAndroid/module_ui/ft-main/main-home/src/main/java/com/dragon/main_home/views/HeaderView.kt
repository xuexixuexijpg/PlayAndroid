package com.dragon.main_home.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.ModelView
import com.dragon.main_home.R
import com.dragon.main_home.databinding.HomeViewHeaderBinding

/**
 * 建议模型view
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
}