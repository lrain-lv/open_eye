package com.app.eye.ui.mvp.model.entity

data class TopicDetailEntity(
    val content: String,
    val headPicture: String,
    val id: Int,
    val joinCount: Int,
    val linkDesc: String,
    val linkUrl: String,
    val shareLink: String,
    val showHotSign: Boolean,
    val tagList: MutableList<TopicTag>,
    val title: String,
    val user: Any,
    val viewCount: Int
)

data class TopicTag(
    val actionUrl: String,
    val adTrack: Any,
    val bgPicture: String,
    val childTagIdList: Any,
    val childTagList: Any,
    val communityIndex: Int,
    val desc: Any,
    val haveReward: Boolean,
    val headerImage: String,
    val id: Int,
    val ifNewest: Boolean,
    val name: String,
    val newestEndTime: Any,
    val tagRecType: String
)