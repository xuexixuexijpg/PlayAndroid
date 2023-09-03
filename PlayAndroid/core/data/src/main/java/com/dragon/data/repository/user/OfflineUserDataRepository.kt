package com.dragon.data.repository.user

import com.dragon.datastore.UserPreferenceDataSource
import com.dragon.model.Userdata
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineUserDataRepository @Inject constructor(
    private val userDataSource : UserPreferenceDataSource
) : UserDataRepository{
    override val userData: Flow<Userdata>
        = userDataSource.userData
}