package com.dragon.ft_main.di.minemodule

import com.dragon.ft_main.di.homemodule.HomeProviderImpl
import com.dragon.ft_main_mine.MineProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@[Module InstallIn(FragmentComponent::class)]
abstract class MineModule {
    @Binds
    abstract fun mineProvider(mineProvider:  MineProviderImpl):MineProvider
}