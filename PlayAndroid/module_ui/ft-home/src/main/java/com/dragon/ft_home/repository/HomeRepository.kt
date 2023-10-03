package com.dragon.ft_home.repository

import com.dragon.data.model.HomeBanner
import com.dragon.data.model.WanAndroidResult
import com.dragon.data.repository.home.IHomeRepository
import retrofit2.Retrofit
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

internal interface HomeApi{
    @GET("banner/json")
    suspend fun getBanner():WanAndroidResult<List<HomeBanner>>
}

@Singleton
class HomeRepository @Inject constructor(
    retrofit : Retrofit
) : IHomeRepository{
    private val netApi  = retrofit.create(HomeApi::class.java)
}