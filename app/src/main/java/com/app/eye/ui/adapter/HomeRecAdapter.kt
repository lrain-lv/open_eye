package com.app.eye.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.eye.R
import com.app.eye.rx.*
import com.app.eye.ui.entity.HomeRecItem
import com.app.eye.ui.entity.HomeRecItem.Companion.HOME_FOLLOWCARD_VIDEO
import com.app.eye.ui.entity.HomeRecItem.Companion.HOME_INFORMATION
import com.app.eye.ui.entity.HomeRecItem.Companion.HOME_TAG_BRIEF_CARD
import com.app.eye.ui.entity.HomeRecItem.Companion.HOME_TEXT_CARD
import com.app.eye.ui.entity.HomeRecItem.Companion.HOME_TOPIC_BRIEF_CARD
import com.app.eye.ui.entity.HomeRecItem.Companion.HOME_UGC_SELECT_CARD
import com.app.eye.ui.entity.HomeRecItem.Companion.HOME_VIDEO_SMALL_CARD
import com.app.eye.widgets.NoScrollLinearLayoutManager
import com.app.eye.widgets.transformations.RoundedCornersTransformation
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.util.*


class HomeRecAdapter(data: MutableList<HomeRecItem>) :
    BaseMultiItemQuickAdapter<HomeRecItem, BaseViewHolder>(data = data), LoadMoreModule {
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
            HOME_FOLLOWCARD_VIDEO -> {
                val data = item.data.content.data
                val videoDuration = data.duration
                var time: String = ""
                if (videoDuration < 60) {
                    time = String.format(Locale.getDefault(), "00:%02d", videoDuration % 60)
                } else if (videoDuration < 3600) {
                    time = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        videoDuration / 60,
                        videoDuration % 60
                    )
                }
                holder.setText(R.id.tv_title, data.title)
                    .setText(R.id.tv_category, item.data.header?.description)
                    .setText(R.id.tv_duration, time)
                val ivCover = holder.getView<ImageView>(R.id.iv_cover)

                ivCover.loadImageWithTransform(
                    data.cover.feed,
                    coil.transform.RoundedCornersTransformation(
                        topLeft = 5f.dp2px(),
                        topRight = 5f.dp2px()
                    ))
                val ivHeader = holder.getView<ImageView>(R.id.iv_header)
                ivHeader.loadImageCircle( item.data.header?.icon, 36f)
            }
            HOME_INFORMATION -> {
                val data = item.data
                val ivCover = holder.getView<ImageView>(R.id.iv_cover)
                val recycler = holder.getView<RecyclerView>(R.id.recycler)
                ivCover.loadImageWithTransform(
                    data.backgroundImage,
                    coil.transform.RoundedCornersTransformation(
                        topLeft = 5f.dp2px(),
                        topRight = 5f.dp2px()
                    ))
                val adapter = DailyInfoAdapter(data.titleList)
                recycler.layoutManager = NoScrollLinearLayoutManager(context,
                    LinearLayoutManager.VERTICAL, false)
                recycler.adapter = adapter
            }
            HOME_VIDEO_SMALL_CARD -> {
                val videoDuration = item.data.duration
                var time: String = ""
                if (videoDuration < 60) {
                    time = String.format(Locale.getDefault(), "00:%02d", videoDuration % 60)
                } else if (videoDuration < 3600) {
                    time = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        videoDuration / 60,
                        videoDuration % 60
                    )
                }
                val img = holder.getView<ImageView>(R.id.iv_pic)
                holder.setText(R.id.tv_video_title, item.data.title)
                    .setText(R.id.tv_category, "${item.data.category} / ${item.data.author.name}")
                    .setText(R.id.tv_duration, time)
                img.loadImageRound( item.data.cover.feed)
            }
            HOME_UGC_SELECT_CARD -> {
                val itemList = item.data.itemList
                itemList.forEachIndexed { index, homeRecItemX ->
                    when (index) {
                        0 -> {
                            val imgleft = holder.getView<ImageView>(R.id.img_left)
                            imgleft.loadImageCommon( homeRecItemX.data.cover.feed)
                        }
                        1 -> {
                            val imgtop = holder.getView<ImageView>(R.id.img_top)
                            imgtop.loadImageCommon( homeRecItemX.data.cover.feed)
                        }
                        2 -> {
                            val imgbottom = holder.getView<ImageView>(R.id.img_bottom)
                            imgbottom.loadImageCommon( homeRecItemX.data.cover.feed)
                        }
                    }
                }
            }
            HOME_TAG_BRIEF_CARD -> {
                val img = holder.getView<ImageView>(R.id.iv_icon)
                val tvAttention = holder.getView<TextView>(R.id.tv_attention)
                tvAttention.isEnabled = false
                tvAttention.text = "+关注"
                holder.setText(R.id.tv_brief_title, item.data.title)
                    .setText(R.id.tv_dec, item.data.description)
                img.loadImageRoundWithSize(item.data.icon, 70f)
            }
            HOME_TOPIC_BRIEF_CARD -> {
                val ivIcon = holder.getView<ImageView>(R.id.iv_icon)
                ivIcon.loadImageRound(item.data.icon)
                holder.setText(R.id.tv_title, item.data.title)
                    .setGone(R.id.iv_fire, !item.data.showHotSign)
                    .setText(R.id.tv_count, item.data.description)
            }
        }
    }

}