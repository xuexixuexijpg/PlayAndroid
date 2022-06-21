package com.dragon.ft_main_home.repo

import com.dragon.ft_main_home.model.TopArticleBean
import com.dragon.module_base.repository.BaseRepository
import com.drake.net.Net
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 数据源
 */
@Singleton
class HomeRepository @Inject constructor():BaseRepository() {

    /**
     * 获取置顶文章
     */
    fun getTopArticle() = request<MutableList<TopArticleBean>> {
        val result = Net.get("article/top/json").execute<MutableList<TopArticleBean>>()
        emit(result)
    }



}