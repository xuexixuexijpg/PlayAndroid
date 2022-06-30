package com.dragon.ft_main_home.views

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_MATCH_HEIGHT,saveViewState = true,)
class GridCarousel(
    context: Context
): Carousel(context) {

    override fun createLayoutManager(): LayoutManager {
        return GridLayoutManager(context, 7, GridLayoutManager.HORIZONTAL, false)
    }

    override fun getSnapHelperFactory(): Nothing? = null
}