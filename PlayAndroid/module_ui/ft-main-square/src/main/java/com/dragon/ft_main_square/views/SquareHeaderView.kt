package com.dragon.ft_main_square.views

import android.annotation.SuppressLint
import android.content.Context
import android.system.Os.bind
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp

import com.dragon.ft_main_square.R
import com.dragon.ft_main_square.databinding.SquareViewHeaderBinding


/**
 * 建立模型view
 */

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class SquareHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(SquareViewHeaderBinding::bind)

    init {
        inflate(context, R.layout.square_view_header,this)
    }

    @CallbackProp
    fun onAvatarClick(listener: OnClickListener?){
        binding.textView.setOnClickListener(listener)
    }

    @TextProp
    fun setText(title: CharSequence?){
        binding.textView.text = title
    }

}