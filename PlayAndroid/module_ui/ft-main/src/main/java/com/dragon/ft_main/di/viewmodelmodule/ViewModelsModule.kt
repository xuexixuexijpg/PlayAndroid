package com.dragon.ft_main.di.viewmodelmodule

import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.MavericksViewModelComponent
import com.airbnb.mvrx.hilt.ViewModelKey
import com.dragon.ft_main_home.viewmodle.ItemTabViewModel
import com.dragon.ft_main_mine.MineViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap

@Module
@InstallIn(MavericksViewModelComponent::class)
interface ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MineViewModel::class)
    fun helloViewModelFactory(factory: MineViewModel.Factory): AssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @ViewModelKey(ItemTabViewModel::class)
    fun itemViewModelFactory(factory: ItemTabViewModel.Factory): AssistedViewModelFactory<*, *>
}