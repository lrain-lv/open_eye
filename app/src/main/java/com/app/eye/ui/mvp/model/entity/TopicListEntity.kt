package com.app.eye.ui.mvp.model.entity

data class TopicListEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<TopicItem>,
    val nextPageUrl: String?,
    val total: Int
)

data class TopicItem(
    val adIndex: Int,
    val `data`: TopicData,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
)

data class TopicData(
    val actionUrl: String,
    val dataType: String,
    val haveReward: Boolean,
    val id: Int,
    val ifNewest: Boolean,
    val imageUrl: String,
    val joinCount: Int,
    val newestEndTime: Long,
    val showHotSign: Boolean,
    val title: String,
    val viewCount: Int
)