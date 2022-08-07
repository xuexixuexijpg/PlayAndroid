package com.dragon.ft_main_home.repo

import android.util.Log
import com.dragon.common_database.dao.LocalReadHistoryDao
import com.dragon.common_database.model.LocalReadHistoryEntity
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
class HomeRepository @Inject constructor(
    private val historyDao: LocalReadHistoryDao
):BaseRepository() {

    /**
     * 插入浏览历史
     */
    suspend fun insertReadHistory(data:List<LocalReadHistoryEntity>){
        historyDao.insertOrIgnoreHistory(data)
    }

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

    /**
     * 获取置顶文章数据
     *
     */
    fun getHomeArticle(page:Int = 0) = request<HomeArticleListBean> {
        val result = Net.get("article/list/${page}/json?page_size=10").execute<HomeArticleListBean>()
        emit(result)
    }

}