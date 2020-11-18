package com.app.eye.ui.mvp.model.entity

import java.io.Serializable

data class BadgeEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<BadgeItem>,
    val nextPageUrl: Any,
    val total: Int
)

data class BadgeItem(
    val adIndex: Int,
    val `data`: BadgeData,
    val id: Int,
    val tag: Any,
    val trackingData: Any,
    val type: String
) : Serializable

data class BadgeData(
    val buttonList: MutableList<BadgeButton>,
    val currentDuration: Int,
    val currentMedalLevel: Int,
    val dataType: String,
    val description: String,
    val functionDesc: String,
    val medalIcon: String,
    val receiveTime: Long?,
    val subTitle: String,
    val tagBgPicture: String,
    val tagId: Int,
    val title: String
) : Serializable

data class BadgeButton(
    val actionUrl: String,
    val highlight: Boolean,
    val ifAdTrack: Boolean,
    val text: String
) : Serializable