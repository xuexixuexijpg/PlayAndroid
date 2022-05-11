package com.dragon.ft_main.di

import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.migration.AliasOf
import javax.inject.Scope

@Scope
@AliasOf(FragmentScoped::class)
annotation class MainScope()
