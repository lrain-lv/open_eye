package com.app.eye.ui.entity

data class TagIndexEntity(
    val tabInfo: TabInfo,
    val tagInfo: TagInfo
)

data class TagInfo(
    val actionUrl: Any,
    val bgPicture: String,
    val childTabList: Any,
    val dataType: String,
    val description: String,
    val follow: Follow,
    val headerImage: String,
    val id: Int,
    val lookCount: Int,
    val name: String,
    val recType: Int,
    val shareLinkUrl: String,
    val tagDynamicCount: Int,
    val tagFollowCount: Int,
    val tagVideoCount: Int
)
