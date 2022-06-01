package com.dragon.ft_main.di.homemodule

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@[Module InstallIn(FragmentComponent::class)]
abstract class HomeModule {
    @Binds
    abstract fun homeProvider(homeProviderImpl: HomeProviderImpl): com.dragon.ft_main_home.HomeProvider
}