package com.dragon.service_di_app.provider.mainui

import com.dragon.ft_main.MainProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent


@Module
@InstallIn(FragmentComponent::class)
abstract class MainModule {
    @Binds
    abstract fun provideMainContent(mainProvider: MainProviderImpl): MainProvider
}