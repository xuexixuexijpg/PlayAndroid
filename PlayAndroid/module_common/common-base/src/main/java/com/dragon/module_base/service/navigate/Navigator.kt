package com.dragon.module_base.service.navigate

import android.content.Context
import android.content.Intent
import androidx.navigation.NavController

//导航
interface Navigator<T : BaseArgs?> {
    fun navigateTo(navController: NavController, navOptions: T?)
}