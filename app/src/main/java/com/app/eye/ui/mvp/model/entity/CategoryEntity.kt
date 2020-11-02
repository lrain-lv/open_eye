package com.app.eye.ui.mvp.model.entity

data class CategoryEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<CategoryItem>,
    val nextPageUrl: String?,
    val total: Int
)

data class CategoryItem(
    val adIndex: Int,
    val `data`: CategoryData,
    val id: Int,
    val type: String
)

data class CategoryData(
    val actionUrl: String?,
    val dataType: String,
    val description: String?,
    val id: Int,
    val image: String,
    val shade: Boolean,
    val title: String
)