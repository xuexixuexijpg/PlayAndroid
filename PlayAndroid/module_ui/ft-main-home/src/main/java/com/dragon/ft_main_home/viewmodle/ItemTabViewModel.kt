package com.dragon.ft_main_home.viewmodle

import android.util.Log
import com.airbnb.mvrx.*
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.dragon.common_database.model.LocalReadHistoryEntity
import com.dragon.common_utils.ext.upsert
import com.dragon.ft_main_home.entity.*
import com.dragon.ft_main_home.repo.HomeRepository
import com.dragon.module_base.event.LoadResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import java.util.Collections.addAll

/**
 * 描述视图的数据
 */

typealias HomeArticles = List<HomeArticleEntity>
data class HomeArticle(
    val isLoadMore: Int = LoadResult.SUCCESS.state,
    val isRefresh: Int = LoadResult.LOADING.state,
    val dataTopArticle: List<TopArticleBean> = emptyList(),
    val dataBanner: List<BannerBean> = emptyList(),
    val dataOfficial: List<OfficialAccountEntity> = emptyList(),
    val dataHomeArticle: List<HomeArticleEntity> = emptyList(),
    val topArticleData: Async<MutableList<TopArticleBean>> = Uninitialized,
    val bannerData: Async<MutableList<BannerBean>> = Uninitialized,
    val officialData: Async<MutableList<OfficialAccountEntity>> = Uninitialized,
    val homeArticleData: Async<HomeArticleListBean> = Uninitialized
) :
    MavericksState

class ItemTabViewModel @AssistedInject constructor(
    @Assisted private val homeData: HomeArticle,
    private val mRepo: HomeRepository
) :
    MavericksViewModel<HomeArticle>(homeData) {

    init {
        initData()
    }

    /**
     * 获取首页初始化数据
     */
    private var getDataJob: Job? = null
    private var currentPage = 0
    fun initData() {
//        getDataJob?.cancel()
//        getDataJob = viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching {
//                Get <MutableList<TopArticleBean>>("article/top/json").await()
//            }.onSuccess {
//
//            }
//        }
        currentPage = 0
        //初始化要保留原来的分页数据
        setState { copy(isRefresh = LoadResult.LOADING.state, dataHomeArticle = dataHomeArticle.take(10)) }
        mRepo.getTopArticle()
            .execute {
                setHasRefresh()
                copy(topArticleData = it, dataTopArticle = it() ?: dataTopArticle)
            }
        mRepo.getBanner()
            .execute(Dispatchers.IO) {
                setHasRefresh()
                copy(bannerData = it, dataBanner = it() ?: dataBanner)
            }
        mRepo.getOfficialAccount()
            .execute {
                setHasRefresh()
                copy(officialData = it, dataOfficial = it() ?: dataOfficial)
            }
        getHomeArticle()
    }


    /**
     * 获取首页文章
     */
    fun getHomeArticle() {
        if (currentPage > 0) setState { copy(isLoadMore = LoadResult.LOADING.state) }
        mRepo.getHomeArticle(currentPage)
            .execute() {
                setLoadMoreState(it)
                //会发射两次Loading 和 Success
                copy(
                    homeArticleData = it, dataHomeArticle = it()?.run {
                        if (currentPage == 0)datas
                        else dataHomeArticle + datas
                    }?:dataHomeArticle
                )
            }
    }

    private fun setLoadMoreState(async: Async<HomeArticleListBean>) {
        if (currentPage > 0 && async is Success){
            //加载更多完成，已经没有更多了
            if (async().pageCount <= currentPage){
                setState { copy(isLoadMore = LoadResult.NO_MORE_DATA.state) }
            }else
            setState { copy(isLoadMore = LoadResult.SUCCESS.state) }
        }
        else if (currentPage > 0 && async is Fail) {
            setState { copy(isLoadMore = LoadResult.FAIL.state) }
        }
        else setHasRefresh()
    }

    /**
     * 处理加载状态
     */
    private fun setHasRefresh() {
        withState {
            if (it.topArticleData is Loading || it.bannerData is Loading || it.officialData is Loading || it.homeArticleData !is Loading) {
                setState { copy(isRefresh = LoadResult.LOADING.state) }
            }
            if (it.topArticleData is Fail && it.bannerData is Fail && it.officialData is Fail && it.homeArticleData is Fail) {
                //全部失败才显示失败，有部分数据显示也是成功
                setState { copy(isRefresh = LoadResult.FAIL.state) }
            }
            if (it.topArticleData !is Loading && it.bannerData !is Loading && it.officialData !is Loading && it.homeArticleData !is Loading) {
                //不是加载中的则三个无论什么状态，有成功就代表加载完成
                if (it.homeArticleData is Fail) {
                    setState {
                        copy(
                            isRefresh = LoadResult.SUCCESS.state,
                            isLoadMore = LoadResult.FAIL.state
                        )
                    }
                } else {
                    setState {
                        copy(
                            isRefresh = LoadResult.SUCCESS.state,
                            isLoadMore = LoadResult.SUCCESS.state
                        )
                    }
                }
            }
        }
    }

    /**
     * 由于execute会执行发射两次Loading,Success事件
     */
    fun changeVmState() {
        currentPage++
    }

    /**
     * 存储
     */
    fun insertReadHistory(data:List<LocalReadHistoryEntity>){
        viewModelScope.launch(Dispatchers.IO) {
            mRepo.insertReadHistory(data)
        }
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ItemTabViewModel, HomeArticle> {
        override fun create(state: HomeArticle): ItemTabViewModel
    }

    companion object :
        MavericksViewModelFactory<ItemTabViewModel, HomeArticle> by hiltMavericksViewModelFactory()
}