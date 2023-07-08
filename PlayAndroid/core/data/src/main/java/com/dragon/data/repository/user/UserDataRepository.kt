package com.dragon.data.repository.user



import com.dragon.model.Userdata
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<Userdata>
}