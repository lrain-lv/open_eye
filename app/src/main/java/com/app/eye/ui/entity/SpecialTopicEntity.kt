package com.app.eye.ui.entity

data class SpecialTopicEntity(
    val count: Int,
    val itemList: MutableList<SpecialTopicItem>,
    val nextPageUrl: String?,
    val total: Int
)

data class SpecialTopicItem(
    val adIndex: Int,
    val `data`: SpecialTopicData,
    val id: Int,
    val type: String
)

data class SpecialTopicData(
    val actionUrl: String,
    val autoPlay: Boolean,
    val dataType: String,
    val description: String,
    val id: Int,
    val image: String,
    val label: SpecialTopicLabel,
    val shade: Boolean,
    val title: String
)

data class SpecialTopicLabel(
    val card: String,
    val text: String
)