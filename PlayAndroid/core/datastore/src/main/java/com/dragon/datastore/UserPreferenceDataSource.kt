package com.dragon.datastore

import androidx.datastore.core.DataStore
import com.dragon.core.datastore.UserPreferences
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferenceDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {
    val userData = userPreferences.data.map {

    }
}