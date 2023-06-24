package com.dragon.data.repository.user

import android.service.autofill.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>
}