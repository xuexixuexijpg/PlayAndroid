package com.dragon.search.di

import androidx.fragment.app.Fragment
import com.dragon.search.SearchFragment
import com.dragon.search.di.SearchScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class SearchModule {
    @Provides @SearchScope
    fun searchFragment() = SearchFragment()
}