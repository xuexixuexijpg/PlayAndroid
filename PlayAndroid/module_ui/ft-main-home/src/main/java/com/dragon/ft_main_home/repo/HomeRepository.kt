package com.dragon.ft_main_home.repo

import android.util.Log
import com.dragon.ft_main_home.entity.BannerBean
import com.dragon.ft_main_home.entity.HomeArticleListBean
import com.dragon.ft_main_home.entity.OfficialAccountEntity
import com.dragon.ft_main_home.entity.TopArticleBean
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

    /**
     * 获取banner
     */
    fun getBanner() = request<MutableList<BannerBean>> {
        val result = Net.get("banner/json").execute<MutableList<BannerBean>>()
        emit(result)
    }

    /**
     * 获取公众号数据
     */
    fun getOfficialAccount() = request<MutableList<OfficialAccountEntity>> {
        val result = Net.get("wxarticle/chapters/json").execute<MutableList<OfficialAccountEntity>>()
        emit(result)
    }

}