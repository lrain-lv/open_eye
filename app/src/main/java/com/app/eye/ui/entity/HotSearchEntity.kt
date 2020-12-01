package com.app.eye.ui.entity


data class HotData(var list: MutableList<HotSearchEntity>)

data class HotSearchEntity(
    val keyword: String,
    val isKeyWord: Boolean = true,
    var isHistory: Boolean = false,
    val isSearch: Boolean = false,
    var searchResult: String = ""
)
