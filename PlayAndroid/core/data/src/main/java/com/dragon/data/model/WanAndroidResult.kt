package com.dragon.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WanAndroidResult<T>(
    val data : T,
    val errorCode : Int,
    val errorMsg : String
)
