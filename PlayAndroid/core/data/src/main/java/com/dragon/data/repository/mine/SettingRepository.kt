package com.dragon.data.repository.mine

import com.dragon.model.Userdata
import kotlinx.coroutines.flow.Flow

interface SettingRepository {
    val userData:Flow<Userdata>

    suspend fun setLanguage(code:String)
}