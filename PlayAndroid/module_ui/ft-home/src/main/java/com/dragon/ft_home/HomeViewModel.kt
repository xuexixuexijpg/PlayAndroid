package com.dragon.ft_home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dragon.common_ui.widgets.search.SearchViewState
import com.dragon.data.repository.search.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class  HomeViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel(){

    private val queryStr = MutableStateFlow("")

    //搜索状态
    val searchState : StateFlow<SearchViewState> = combine(
        queryStr,
        searchRepository.getHotKeys(),
        searchRepository.getSearchData(""),
        ::SearchViewState
    ).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SearchViewState.Empty,
    )

    init {
        viewModelScope.launch {
            queryStr.debounce(300)
                .onEach { query ->
                    val job = launch {
                        searchRepository.getSearchData(query)
                    }
//                    job.invokeOnCompletion { loadingState.removeLoader() }
                    job.join()
                }.catch { throwStr ->
                    Log.e("错误", ": $throwStr")
                }
                .collect()
        }
    }

    fun search(searchTerm: String) {
        queryStr.value = searchTerm
    }
}