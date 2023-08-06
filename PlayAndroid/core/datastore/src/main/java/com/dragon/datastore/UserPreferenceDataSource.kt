package com.dragon.datastore


import androidx.datastore.core.DataStore
import com.dragon.core.datastore.DarkThemeConfigProto
import com.dragon.core.datastore.ThemeBrandProto
import com.dragon.core.datastore.UserPreferences
import com.dragon.core.datastore.copy
import com.dragon.model.DarkThemeConfig
import com.dragon.model.ThemeBrand
import com.dragon.model.User
import com.dragon.model.Userdata
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    /**
     * 数据封装
     */
    val userData = userPreferences.data.map {
        Userdata(
            userName = it.userName,
            password = it.password,
            themeBrand = when (it.themeBrand) {
                null, ThemeBrandProto.THEME_BRAND_UNSPECIFIED,
                ThemeBrandProto.UNRECOGNIZED,
                ThemeBrandProto.THEME_BRAND_DEFAULT -> ThemeBrand.DEFAULT

                ThemeBrandProto.THEME_BRAND_USER -> ThemeBrand.CUSTOM
            },
            darkThemeConfig = when (it.darkThemeConfig) {
                null,
                DarkThemeConfigProto.DARK_THEME_CONFIG_UNSPECIFIED,
                DarkThemeConfigProto.UNRECOGNIZED,
                DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM,
                ->
                    DarkThemeConfig.FOLLOW_SYSTEM

                DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT ->
                    DarkThemeConfig.LIGHT

                DarkThemeConfigProto.DARK_THEME_CONFIG_DARK -> DarkThemeConfig.DARK
            },
            useDynamicColor = it.useDynamicColor,
            shouldHideOnboarding = it.shouldHideOnboarding,
            language = it.language
        )
    }

    suspend fun setLanguage(string: String){
        userPreferences.updateData {
            it.copy {
                this.language = language
            }
        }
    }

    suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        userPreferences.updateData {
            it.copy {
                this.themeBrand = when (themeBrand) {
                    ThemeBrand.DEFAULT -> ThemeBrandProto.THEME_BRAND_DEFAULT
                    ThemeBrand.CUSTOM -> ThemeBrandProto.THEME_BRAND_USER
                }
            }
        }
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.useDynamicColor = useDynamicColor
            }
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData {
            it.copy {
                this.darkThemeConfig = when (darkThemeConfig) {
                    DarkThemeConfig.FOLLOW_SYSTEM ->
                        DarkThemeConfigProto.DARK_THEME_CONFIG_FOLLOW_SYSTEM

                    DarkThemeConfig.LIGHT -> DarkThemeConfigProto.DARK_THEME_CONFIG_LIGHT
                    DarkThemeConfig.DARK -> DarkThemeConfigProto.DARK_THEME_CONFIG_DARK
                }
            }
        }
    }

    suspend fun setUserNameAndPass(user: User) {
        userPreferences.updateData {
            it.copy {
                this.userName = user.userName
                this.password = user.password
            }
        }
    }
}