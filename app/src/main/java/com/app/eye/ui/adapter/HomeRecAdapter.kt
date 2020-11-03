package com.app.eye.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.HomeRecItem
import com.app.eye.ui.mvp.model.entity.HomeRecItem.Companion.HOME_FOLLOWCARD_VIDEO
import com.app.eye.ui.mvp.model.entity.HomeRecItem.Companion.HOME_INFORMATION
import com.app.eye.ui.mvp.model.entity.HomeRecItem.Companion.HOME_TAG_BRIEF_CARD
import com.app.eye.ui.mvp.model.entity.HomeRecItem.Companion.HOME_TEXT_CARD
import com.app.eye.ui.mvp.model.entity.HomeRecItem.Companion.HOME_TOPIC_BRIEF_CARD
import com.app.eye.ui.mvp.model.entity.HomeRecItem.Companion.HOME_UGC_SELECT_CARD
import com.app.eye.ui.mvp.model.entity.HomeRecItem.Companion.HOME_VIDEO_SMALL_CARD
import com.app.eye.widgets.NoScrollLinearLayoutManager
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.util.*


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
                    .setText(R.id.tv_category, "${data.author.name} / #${data.category}")
                    .setText(R.id.tv_duration, time)
                val ivCover = holder.getView<ImageView>(R.id.iv_cover)
                Glide.with(context)
                    .load(data.cover.feed)
                    .transform(
                        CenterCrop(),
                        RoundedCornersTransformation(
                            SizeUtils.dp2px(5f),
                            0,
                            RoundedCornersTransformation.CornerType.TOP
                        )
                    ).into(ivCover)
                val ivHeader = holder.getView<ImageView>(R.id.iv_header)
                Glide.with(context)
                    .load(data.author.icon)
                    .override(SizeUtils.dp2px(36f), SizeUtils.dp2px(36f))
                    .circleCrop()
                    .into(ivHeader)
            }
            HOME_INFORMATION -> {
                val data = item.data
                val ivCover = holder.getView<ImageView>(R.id.iv_cover)
                val recycler = holder.getView<RecyclerView>(R.id.recycler)
                Glide.with(context)
                    .load(data.backgroundImage)
                    .transform(
                        CenterCrop(),
                        RoundedCornersTransformation(
                            SizeUtils.dp2px(5f),
                            0,
                            RoundedCornersTransformation.CornerType.TOP
                        )
                    ).into(ivCover)
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
                var img = holder.getView<ImageView>(R.id.iv_pic)
                holder.setText(R.id.tv_video_title, item.data.title)
                    .setText(R.id.tv_category, "${item.data.category} / ${item.data.author.name}")
                    .setText(R.id.tv_duration, time)
                Glide.with(context)
                    .load(item.data.cover.feed)
                    .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(img)
            }
            HOME_UGC_SELECT_CARD -> {
                val itemList = item.data.itemList
                itemList.forEachIndexed { index, homeRecItemX ->
                    when (index) {
                        0 -> {
                            val imgleft = holder.getView<ImageView>(R.id.img_left)
                            Glide.with(context)
                                .load(homeRecItemX.data.cover.feed)
                                .transform(CenterCrop(),
                                    RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
                                .into(imgleft)
                        }
                        1 -> {
                            val imgtop = holder.getView<ImageView>(R.id.img_top)
                            Glide.with(context)
                                .load(homeRecItemX.data.cover.feed)
                                .transform(CenterCrop(),
                                    RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
                                .into(imgtop)
                        }
                        2 -> {
                            val imgbottom = holder.getView<ImageView>(R.id.img_bottom)
                            Glide.with(context)
                                .load(homeRecItemX.data.cover.feed)
                                .transform(CenterCrop(),
                                    RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
                                .into(imgbottom)
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
                Glide.with(context)
                    .load(item.data.icon)
                    .override(SizeUtils.dp2px(70f), SizeUtils.dp2px(70f))
                    .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(img)
            }
            HOME_TOPIC_BRIEF_CARD -> {
                val ivIcon = holder.getView<ImageView>(R.id.iv_icon)
                Glide.with(context)
                    .load(item.data.icon)
                    .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
                    .into(ivIcon)
                holder.setText(R.id.tv_title, item.data.title)
                    .setGone(R.id.iv_fire, !item.data.showHotSign)
                    .setText(R.id.tv_count, item.data.description)
            }
        }
    }

}