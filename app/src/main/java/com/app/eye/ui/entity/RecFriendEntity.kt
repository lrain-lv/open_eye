package com.app.eye.ui.entity

data class RecFriendEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<RecItem>,
    val nextPageUrl: String?,
    val total: Int
)

data class RecItem(
    val adIndex: Int,
    val `data`: RecData,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
)

data class RecData(
    val actionUrl: String,
    val adTrack: Any,
    val dataType: String,
    val description: String,
    val expert: Boolean,
    val follow: RecFollow?,
    val icon: String,
    val iconType: String,
    val id: Int,
    val ifPgc: Boolean,
    val ifShowNotificationIcon: Boolean,
    val subTitle: Any,
    val title: String,
    val uid: Int
)

data class RecFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
)