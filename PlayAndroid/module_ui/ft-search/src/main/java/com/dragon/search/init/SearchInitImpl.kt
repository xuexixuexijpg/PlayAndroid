package com.dragon.search.init

import android.app.Application
import android.util.Log
import com.dragon.service_base.appinitializers.AppInitializer
import javax.inject.Inject

class SearchInitImpl @Inject constructor() : AppInitializer{
    override fun init(application: Application) {
        //
        Log.e("AppInitializer", "init: ", )
    }
}