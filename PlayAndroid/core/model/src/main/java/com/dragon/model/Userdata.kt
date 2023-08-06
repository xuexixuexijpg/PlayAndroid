package com.dragon.model

data class Userdata (
    val userName: String,
    val password: String,
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val shouldHideOnboarding: Boolean,
    val language : String
)
