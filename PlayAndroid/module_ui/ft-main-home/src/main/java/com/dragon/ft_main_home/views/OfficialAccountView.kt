package com.dragon.ft_main_home.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.ModelView
import com.dragon.ft_main_home.R
import com.dragon.ft_main_home.databinding.HomeOfficialViewBinding

/**
 * 公众号
 */
@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class OfficialAccountView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(HomeOfficialViewBinding::bind)

    init {
        inflate(context,R.layout.home_official_view,this)
    }
}