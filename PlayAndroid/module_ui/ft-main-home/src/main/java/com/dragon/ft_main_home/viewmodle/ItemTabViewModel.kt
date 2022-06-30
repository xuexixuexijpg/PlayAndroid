package com.dragon.ft_main_home.viewmodle

import androidx.lifecycle.Transformations.map
import androidx.lifecycle.ViewModel
import com.airbnb.mvrx.*
import com.airbnb.mvrx.hilt.AssistedViewModelFactory
import com.airbnb.mvrx.hilt.hiltMavericksViewModelFactory
import com.dragon.ft_main_home.entity.BannerBean
import com.dragon.ft_main_home.entity.HomeArticleListBean
import com.dragon.ft_main_home.entity.OfficialAccountEntity
import com.dragon.ft_main_home.entity.TopArticleBean
import com.dragon.ft_main_home.repo.HomeRepository
import com.dragon.module_base.event.LoadResult
import com.drake.net.Get
import com.drake.net.Net
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlin.coroutines.suspendCoroutine

/**
 * 描述视图的数据
 */
data class HomeArticle(
    val isRefresh: Int = LoadResult.LOADING.state,
    val dataTopArticle: List<TopArticleBean> = emptyList(),
    val dataBanner: List<BannerBean> = emptyList(),
    val dataOfficial:List<OfficialAccountEntity> = emptyList(),
    val topArticleData: Async<MutableList<TopArticleBean>> = Uninitialized,
    val bannerData: Async<MutableList<BannerBean>> = Uninitialized,
    val officialData: Async<MutableList<OfficialAccountEntity>> = Uninitialized
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
    private  var getDataJob:Job?=null
    fun initData() {
//        getDataJob?.cancel()
//        getDataJob = viewModelScope.launch(Dispatchers.IO) {
//            kotlin.runCatching {
//                Get <MutableList<TopArticleBean>>("article/top/json").await()
//            }.onSuccess {
//
//            }
//        }
        setState { copy(isRefresh = LoadResult.LOADING.state) }
        mRepo.getTopArticle()
            .execute {
                setHasRefresh()
                copy(topArticleData = it, dataTopArticle = it() ?: dataTopArticle)
            }
        mRepo.getBanner()
            .execute(Dispatchers.IO) {
                setHasRefresh()
                copy(bannerData = it,dataBanner =  it() ?: dataBanner)
            }
        mRepo.getOfficialAccount()
            .execute {
                setHasRefresh()
                copy(officialData = it,dataOfficial = it()?:dataOfficial)
            }
    }

    /**
     * 处理加载状态
     */
    private fun setHasRefresh() {
        withState {
            if (it.topArticleData is Loading || it.bannerData is Loading || it.officialData is Loading){
                setState { copy(isRefresh = LoadResult.LOADING.state) }
            }
            if (it.topArticleData is Fail && it.bannerData is Fail && it.officialData is Fail){
                //全部失败才显示失败，有部分数据显示也是成功
                setState { copy(isRefresh = LoadResult.FAIL.state) }
            }
            if (it.topArticleData !is Loading && it.bannerData !is Loading && it.officialData !is Loading){
                //不是加载中的则三个无论什么状态，有成功就代表加载完成
                setState { copy(isRefresh = LoadResult.SUCCESS.state) }
            }
        }
    }


    @AssistedFactory
    interface Factory : AssistedViewModelFactory<ItemTabViewModel, HomeArticle> {
        override fun create(state: HomeArticle): ItemTabViewModel
    }

    companion object :
        MavericksViewModelFactory<ItemTabViewModel, HomeArticle> by hiltMavericksViewModelFactory()
}