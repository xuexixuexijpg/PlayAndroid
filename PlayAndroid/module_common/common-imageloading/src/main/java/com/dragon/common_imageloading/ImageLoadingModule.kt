package com.dragon.common_imageloading
import com.dragon.service_base.appinitializers.AppInitializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@InstallIn(SingletonComponent::class)
@Module
abstract class ImageLoadingModule {
    @Binds
    @IntoSet
    abstract fun provideCoilInitializer(bind: CoilAppInitializer): AppInitializer
}
