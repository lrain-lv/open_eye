package com.app.eye.ui.entity

import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.entity.MultiItemEntity

data class BrandListEntity(
    val code: String,
    val message: BrandListMessage,
    val result: BrandListResult
)

data class BrandListMessage(
    val action: String,
    val content: String
)

data class BrandListResult(
    val item_list: MutableList<BrandListItem>
)

data class BrandListItem(
    val items: MutableList<BrandListItemX>,
    val key: String
)

data class BrandListItemX(
    val link: String?,
    val name: String
) : MultiItemEntity {
    override val itemType: Int
        get() = if (StringUtils.isEmpty(link)) 0 else 1
}