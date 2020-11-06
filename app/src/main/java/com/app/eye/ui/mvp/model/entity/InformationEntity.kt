package com.app.eye.ui.mvp.model.entity

import com.app.eye.ui.mvp.model.entity.ItemDaily.Companion.DAILY_INFORMATION
import com.app.eye.ui.mvp.model.entity.ItemDaily.Companion.DAILY_TEXT
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.entity.MultiItemEntity

data class InformationEntity(
    val adExist: Boolean,
    val count: Int,
    val itemList: MutableList<InfoItem>,
    val nextPageUrl: String,
    val total: Int
)

data class InfoItem(
    val adIndex: Int,
    val `data`: InfoData,
    val id: Int,
    val tag: Any,
    val type: String
) : MultiItemEntity {
    override val itemType: Int
        get() =
            if (StringUtils.equals("textCard", type)) DAILY_TEXT else DAILY_INFORMATION

}

data class InfoData(
    val actionUrl: String?,
    val backgroundImage: String,
    val bannerList: MutableList<InfoBanner>,
    val dataType: String,
    val follow: Any,
    val headerType: String,
    val id: Int,
    val startTime: Long,
    val subTitle: Any,
    val text: String,
    val titleList: MutableList<String>,
    val type: String
)

data class InfoBanner(
    val background_image: String,
    val link: String,
    val poster_image: String,
    val tag_name: String,
    val title: String
)