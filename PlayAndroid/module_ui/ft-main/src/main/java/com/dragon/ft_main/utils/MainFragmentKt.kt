package com.dragon.ft_main.utils

import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

@MainThread
public inline fun <reified VM : ViewModel> Fragment.navGraphViewModels(
    @IdRes navGraphId: Int,
    navController: NavController? = null,
    noinline extrasProducer: (() -> CreationExtras)? = null,
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    var backStackEntry : NavBackStackEntry? = null

    if (navController == null){
        backStackEntry =
            findNavController().getBackStackEntry(navGraphId)

    }else {
        backStackEntry = navController.getBackStackEntry(navGraphId)
    }
//    val backStackEntry by lazy {
//        findNavController().getBackStackEntry(navGraphId)
//    }
    val storeProducer: () -> ViewModelStore = {
        backStackEntry.viewModelStore
    }
    return createViewModelLazy(
        VM::class, storeProducer,
        { extrasProducer?.invoke() ?: backStackEntry.defaultViewModelCreationExtras },
        factoryProducer ?: { backStackEntry.defaultViewModelProviderFactory }
    )
}