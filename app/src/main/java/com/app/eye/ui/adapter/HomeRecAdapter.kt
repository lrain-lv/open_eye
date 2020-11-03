package com.app.eye.ui.adapter

import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.HomeRecItem
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

const val HOME_TEXT_CARD = 0
const val HOME_FOLLOWCARD_VIDEO = 1
const val HOME_INFORMATION = 2
const val HOME_VIDEO_SMALL_CARD = 3
const val HOME_UGC_SELECT_CARD = 4
const val HOME_TAG_BRIEF_CARD = 5
const val HOME_TOPIC_BRIEF_CARD = 6
const val NONE = -1

class HomeRecAdapter(data: MutableList<HomeRecItem>) :
    BaseMultiItemQuickAdapter<HomeRecItem, BaseViewHolder>(data = data) {
    init {
        addItemType(HomeRecItem.NONE, R.layout.layout_none)
        addItemType(HomeRecItem.HOME_TEXT_CARD, R.layout.layout_discover_text_card)
        addItemType(HomeRecItem.HOME_FOLLOWCARD_VIDEO, R.layout.layout_daily_video_card)
        addItemType(HomeRecItem.HOME_INFORMATION, R.layout.layout_daily_infomation_card)
        addItemType(HomeRecItem.HOME_VIDEO_SMALL_CARD, R.layout.layout_discover_video_card)
        addItemType(HomeRecItem.HOME_UGC_SELECT_CARD, R.layout.layout_home_rec_ugc_select)
        addItemType(HomeRecItem.HOME_TAG_BRIEF_CARD, R.layout.layout_discover_brief_card)
        addItemType(HomeRecItem.HOME_TOPIC_BRIEF_CARD, R.layout.layout_topic_list_item)
    }

    override fun convert(holder: BaseViewHolder, item: HomeRecItem) {
        when (item.itemType) {
            HOME_TEXT_CARD -> {
                holder.setText(R.id.tv_title, item.data.text)
                    .setText(R.id.tv_right_text, item.data.rightText ?: "")
                    .setGone(R.id.tv_right_text, item.data.rightText.isNullOrEmpty())
            }
        }
    }

}