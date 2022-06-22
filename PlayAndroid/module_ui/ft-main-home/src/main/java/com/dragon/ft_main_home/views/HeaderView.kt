package com.dragon.ft_main_home.views

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.dragon.ft_main_home.R
import com.dragon.ft_main_home.databinding.HomeViewHeaderBinding


/**
 * 建立模型view
 */

class HeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by viewBinding(HomeViewHeaderBinding::bind)

    init {
        inflate(context, R.layout.home_view_header,this)
        //coil加载图片 此时的coil的imageLoader已初始化，全局共用一个实例
        binding.imgAvatar.load(R.drawable.im_header){
            crossfade(true)
        }
        setBackgroundColor(ContextCompat.getColor(context,R.color.white))
    }

    fun onAvatarClick(listener: OnClickListener?){
        binding.imgAvatar.setOnClickListener(listener)
    }

    fun onSearchLayoutClick(listener: OnClickListener?){
        binding.layoutSearchArea.setOnClickListener(listener)
    }

    fun setHintText(title: CharSequence?){
        binding.tvSearch.text = title
    }

}