package com.dragon.module_base.base.activity

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.airbnb.mvrx.MavericksView
import com.dragon.module_base.R

class BaseActivity(@LayoutRes id: Int) : AppCompatActivity(id), MavericksView {
    override fun invalidate() {

    }
}