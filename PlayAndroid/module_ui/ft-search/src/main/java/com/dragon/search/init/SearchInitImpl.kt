package com.dragon.search.init

import android.app.Application
import android.util.Log
import com.dragon.module_base.service.appinitializers.AppInitializer
import javax.inject.Inject

class SearchInitImpl @Inject constructor() : AppInitializer{
    override fun init(application: Application) {
        //
        Log.e("AppInitializer", "init: ", )
    }
}