package com.dragon.ft_main_home.views

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import by.kirich1409.viewbindingdelegate.viewBinding
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.TextProp
import com.dragon.ft_main_home.R
import com.dragon.module_base.event.LoadResult

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class LoadMoreTipView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        top = 5
        textSize = 22f
        gravity = Gravity.CENTER
    }

    @ModelProp
    fun setLoadMoreState(state:Int){
        when(state){
            LoadResult.LOADING.state  -> text = "正在加载中"
            LoadResult.FAIL.state -> text = "加载失败"
            LoadResult.NO_MORE_DATA.state -> text = "没有更多数据了"
        }
    }
}