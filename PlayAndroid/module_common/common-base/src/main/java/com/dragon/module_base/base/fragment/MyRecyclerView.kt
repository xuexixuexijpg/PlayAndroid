package com.dragon.module_base.base.fragment

import android.content.Context
import android.util.AttributeSet
import com.airbnb.epoxy.EpoxyRecyclerView

internal class MyRecyclerView : EpoxyRecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
//        adapter = null
        swapAdapter(null, true)
        // Or use swapAdapter(null, true) so that the existing views are recycled to the view pool
    }
}