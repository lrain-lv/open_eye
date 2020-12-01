package com.app.eye.ui.entity

import java.io.Serializable

data class TabChildEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<TabItem>,
    val nextPageUrl: String?,
    val total: Int
)

data class TabItem(
    val adIndex: Int,
    val `data`: TabData,
    val id: Int,
    val type: String
):Serializable

data class TabData(
    val actionUrl: String,
    val dataType: String,
    val description: String,
    val expert: Boolean,
    val follow: TabFollow,
    val haveReward: Boolean,
    val icon: String,
    val iconType: String,
    val id: Int,
    val ifNewest: Boolean,
    val ifPgc: Boolean,
    val ifShowNotificationIcon: Boolean,
    val medalIcon: Boolean,
    val subTitle: String,
    val switchStatus: Boolean,
    val title: String,
    val uid: Int
):Serializable

data class TabFollow(
    val followed: Boolean,
    val itemId: Int,
    val itemType: String
):Serializable