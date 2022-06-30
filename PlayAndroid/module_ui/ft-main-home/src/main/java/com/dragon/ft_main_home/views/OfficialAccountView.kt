package com.dragon.ft_main_home.views

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.ColorUtils
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.dragon.ft_main_home.R
import com.dragon.ft_main_home.databinding.HomeOfficialViewBinding
import kotlin.random.Random

/**
 * 公众号
 */
@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT, saveViewState = true)
class OfficialAccountView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(HomeOfficialViewBinding::bind)

    init {
        inflate(context,R.layout.home_official_view,this)
        id = R.id.save_state
        val gradientDrawable = GradientDrawable()
        gradientDrawable.shape = GradientDrawable.OVAL
        gradientDrawable.setColor(0xff000000.toInt() or Random.nextInt(0x00ffffff))
        binding.imageView2.load(gradientDrawable){
            crossfade(true)
        }
    }

    @TextProp
    fun setAuthorName(s:CharSequence?){
        binding.tvAuthor.text = s
        binding.tvHeadUsername.text = s?.subSequence(0,1)?:""
    }


}