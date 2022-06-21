package com.dragon.search.di

import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import com.dragon.search.SearchProvider
import javax.inject.Inject

//提供主页服务注入
class SearchProviderImpl @Inject constructor(
    private val activity: FragmentActivity,
) : SearchProvider {
    override fun navigateTo(navController: NavController) {

    }
}