package com.app.eye.ui.adapter

import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import com.app.eye.R
import com.app.eye.rx.loadImageCircle
import com.app.eye.rx.loadImageRound
import com.app.eye.rx.loadImageRoundWithSize
import com.app.eye.ui.entity.SearchItem
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.util.*

class SearchAdapter(dataList: MutableList<SearchItem>) :
    BaseMultiItemQuickAdapter<SearchItem, BaseViewHolder>(), LoadMoreModule {

    init {
        addItemType(SearchItem.NONE, R.layout.layout_none)
        addItemType(SearchItem.SEARCH_TEXT, R.layout.layout_discover_text_card)
        addItemType(SearchItem.SEARCH_VIDEO, R.layout.layout_discover_video_card)
        addItemType(SearchItem.SEARCH_BRIEF_CARD, R.layout.layout_discover_brief_card)
    }

    override fun convert(holder: BaseViewHolder, item: SearchItem) {
        when (item.itemType) {
            SearchItem.SEARCH_TEXT -> {
                holder.setText(R.id.tv_title, item.data.text)
                    .setText(R.id.tv_right_text, "显示全部")
                    .setGone(R.id.tv_right_text, item.data.actionUrl.isNullOrEmpty())
                val tvRight = holder.getView<TextView>(R.id.tv_right_text)
                tvRight.setCompoundDrawables(null, null, null, null)
            }
            SearchItem.SEARCH_VIDEO -> {
                val img = holder.getView<ImageView>(R.id.iv_pic)
                val videoDuration = item.data.duration
                var time: String = ""
                if (videoDuration < 60) {
                    var time = String.format(Locale.getDefault(), "00:%02d", videoDuration % 60)
                } else if (videoDuration < 3600) {
                    time = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        videoDuration / 60,
                        videoDuration % 60
                    )
                }
                holder.setText(R.id.tv_duration, time)
                    .setText(R.id.tv_video_title, item.data.title)
                    .setText(R.id.tv_category, "#${item.data.category}")
                img.loadImageRound(context, item.data.cover.feed)
            }
            SearchItem.SEARCH_BRIEF_CARD -> {
                val iconType = item.data.iconType
                holder.setText(R.id.tv_brief_title, item.data.title)
                    .setText(R.id.tv_dec, item.data.description)
                    .setGone(R.id.tv_attention, item.data.follow == null)
                val ivIcon = holder.getView<ImageView>(R.id.iv_icon)
                if (TextUtils.equals("round", iconType)) {
                    ivIcon.loadImageCircle(context, item.data.icon, 70f)
                } else {
                    ivIcon.loadImageRoundWithSize(context, item.data.icon, 70f)
                }
            }
        }
    }
}