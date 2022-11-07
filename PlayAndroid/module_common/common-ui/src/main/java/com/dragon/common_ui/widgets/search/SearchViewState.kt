package com.dragon.common_ui.widgets.search

data class SearchViewState(
    val query: String = "",
    val hintStr: String = "",
    val searchResults: List<String> = emptyList(),
) {
    companion object{
        val Empty = SearchViewState()
    }
}