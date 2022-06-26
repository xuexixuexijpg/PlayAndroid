package com.dragon.module_base.base

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner


open class BaseApplication : Application() , ViewModelStoreOwner {

    private val mViewModelStore by lazy { ViewModelStore() }

    override fun onCreate() {
        super.onCreate()
    }

    override fun getViewModelStore(): ViewModelStore {
        return mViewModelStore
    }

    /**
     * 获取 Application 上下文的 ViewModel
     * 注意: Application 需要实现 ViewModelStoreOwner 接口.
     */
    fun <VM : ViewModel> getAndroidViewModel(modelClass: Class<VM>): VM {
        return ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        )[modelClass]
    }
}