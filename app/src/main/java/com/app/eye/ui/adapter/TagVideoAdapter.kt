package com.app.eye.ui.adapter


import com.app.eye.ui.mvp.model.entity.TagVideoItem
import android.graphics.Color
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import com.app.eye.R
import com.app.eye.widgets.transformations.RoundedCornersTransformation
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.TimeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

import java.util.*

class TagVideoAdapter(data: MutableList<TagVideoItem>) :
    BaseMultiItemQuickAdapter<TagVideoItem, BaseViewHolder>(), LoadMoreModule {

    init {
        addItemType(TagVideoItem.TYPE_NONE, R.layout.layout_none)
        addItemType(TagVideoItem.TYPE_VIDEO, R.layout.layout_tab_video_item_video)
        addItemType(TagVideoItem.TYPE_PIC2, R.layout.layout_tab_video_item_pic2)
        addItemType(TagVideoItem.TYPE_PIC3, R.layout.layout_tab_video_item_pic3)
        addItemType(TagVideoItem.TYPE_PIC4, R.layout.layout_tab_video_item_pic4)
        addItemType(TagVideoItem.TYPE_TEXT_CARD, R.layout.layout_discover_text_card)
        addItemType(TagVideoItem.TYPE_FOLLOW_CARD, R.layout.layout_daily_video_card)
        addItemType(TagVideoItem.TYPE_VIDEO_SMALL_CARD, R.layout.layout_discover_video_card)
    }

    override fun convert(holder: BaseViewHolder, item: TagVideoItem) {
        if (item.itemType == TagVideoItem.TYPE_NONE) return
        when (item.itemType) {
            TagVideoItem.TYPE_TEXT_CARD -> {
                holder.setText(R.id.tv_title, item.data.text)
                    .setText(R.id.tv_right_text,
                        if (item.data.actionUrl.isNullOrEmpty()) "" else "查看全部")
                    .setGone(R.id.tv_right_text, item.data.actionUrl.isNullOrEmpty())
            }
            TagVideoItem.TYPE_FOLLOW_CARD -> {
                val data = item.data.content!!.data
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
            TagVideoItem.TYPE_VIDEO_SMALL_CARD -> {
                val video_duration = item.data.duration
                var time: String = ""
                if (video_duration < 60) {
                    time = String.format(Locale.getDefault(), "00:%02d", video_duration % 60)
                } else if (video_duration < 3600) {
                    time = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        video_duration / 60,
                        video_duration % 60
                    )
                }
                val img = holder.getView<ImageView>(R.id.iv_pic)
                holder.setText(R.id.tv_video_title, item.data.title)
                    .setText(R.id.tv_category, "${item.data.category} / ${item.data.author.name}")
                    .setText(R.id.tv_duration, time)
                Glide.with(context)
                    .load(item.data.cover.feed)
                    .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(img)
            }
            else -> {
                val ivHeader = holder.getView<ImageView>(R.id.iv_header)
                val data = item.data.content!!.data
                val header = item.data.header
                Glide.with(context)
                    .load(header.icon)
                    .circleCrop()
                    .override(SizeUtils.dp2px(40f), SizeUtils.dp2px(40f))
                    .into(ivHeader)
                val tvDate = holder.getView<TextView>(R.id.tv_date)
                var create: SpannableStringBuilder? = null
                if (data.area.isNullOrEmpty()) {
                    create = SpanUtils.with(tvDate)
                        .append("${TimeUtils.getFriendlyTimeSpanByNow(item.data.content.data.createTime)}发布：")
                        .create()
                } else {
                    create = SpanUtils.with(tvDate)
                        .append("${TimeUtils.getFriendlyTimeSpanByNow(item.data.content.data.createTime)} 在")
                        .appendImage(R.mipmap.position, SpanUtils.ALIGN_CENTER)
                        .append(data.area)
                        .setBold()
                        .setForegroundColor(Color.BLACK)
                        .append("发布：")
                        .create()
                }
                holder.setText(R.id.tv_name, header.issuerName)
                    .setText(R.id.tv_date, create)
                    .setText(R.id.tv_tag, data.tags[0].name)
                    .setText(R.id.tv_content, data.description)
                    .setGone(R.id.tv_content, data.description.isNullOrEmpty())
                    .setText(R.id.tv_like_count, data.consumption.collectionCount.toString())
                    .setText(R.id.tv_reply_count, data.consumption.replyCount.toString())

                when (item.itemType) {
                    TagVideoItem.TYPE_VIDEO -> {
                        holder.setGone(R.id.iv_play,
                            !TextUtils.equals("video", item.data.content.type))
                        val ivVideo = holder.getView<ImageView>(R.id.iv_video)
                        Glide.with(context)
                            .load(data.cover.feed)
                            .transform(CenterCrop(),
                                RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
                            .into(ivVideo)
                    }
                    TagVideoItem.TYPE_PIC2 -> {
                        val pic1 = holder.getView<ImageView>(R.id.iv_pic1)
                        val pic2 = holder.getView<ImageView>(R.id.iv_pic2)
                        Glide.with(context)
                            .load(data.urls?.get(0))
                            .transform(CenterCrop(),
                                RoundedCornersTransformation(SizeUtils.dp2px(5f),
                                    0,
                                    RoundedCornersTransformation.CornerType.LEFT))
                            .into(pic1)
                        Glide.with(context)
                            .load(data.urls?.get(1))
                            .transform(CenterCrop(),
                                RoundedCornersTransformation(SizeUtils.dp2px(5f),
                                    0,
                                    RoundedCornersTransformation.CornerType.RIGHT))
                            .into(pic2)
                    }
                    TagVideoItem.TYPE_PIC3 -> {
                        val pic1 = holder.getView<ImageView>(R.id.iv_pic1)
                        val pic2 = holder.getView<ImageView>(R.id.iv_pic2)
                        val pic3 = holder.getView<ImageView>(R.id.iv_pic3)
                        Glide.with(context)
                            .load(data.urls!![0])
                            .transform(CenterCrop(),
                                RoundedCornersTransformation(SizeUtils.dp2px(5f),
                                    0,
                                    RoundedCornersTransformation.CornerType.LEFT))
                            .into(pic1)
                        Glide.with(context)
                            .load(data.urls[1])
                            .transform(CenterCrop(),
                                RoundedCornersTransformation(SizeUtils.dp2px(5f),
                                    0,
                                    RoundedCornersTransformation.CornerType.TOP_RIGHT))
                            .into(pic2)
                        Glide.with(context)
                            .load(data.urls[2])
                            .transform(CenterCrop(),
                                RoundedCornersTransformation(SizeUtils.dp2px(5f),
                                    0,
                                    RoundedCornersTransformation.CornerType.BOTTOM_RIGHT))
                            .into(pic3)
                    }
                    TagVideoItem.TYPE_PIC4 -> {
                        holder.setGone(R.id.tv_more, data.urls!!.size == 4)
                            .setText(R.id.tv_more,
                                if (data.urls.size > 4) "+${data.urls.size - 4}" else "")
                        val pic1 = holder.getView<ImageView>(R.id.iv_pic1)
                        val pic0 = holder.getView<ImageView>(R.id.iv_pic0)
                        val pic2 = holder.getView<ImageView>(R.id.iv_pic2)
                        val pic3 = holder.getView<ImageView>(R.id.iv_pic3)
                        Glide.with(context)
                            .load(data.urls[0])
                            .transform(CenterCrop(),
                                RoundedCornersTransformation(SizeUtils.dp2px(5f),
                                    0,
                                    RoundedCornersTransformation.CornerType.TOP_LEFT))
                            .into(pic0)
                        Glide.with(context)
                            .load(data.urls[1])
                            .transform(CenterCrop(),
                                RoundedCornersTransformation(SizeUtils.dp2px(5f),
                                    0,
                                    RoundedCornersTransformation.CornerType.TOP_RIGHT))
                            .into(pic1)
                        Glide.with(context)
                            .load(data.urls[2])
                            .transform(CenterCrop(),
                                RoundedCornersTransformation(SizeUtils.dp2px(5f),
                                    0,
                                    RoundedCornersTransformation.CornerType.BOTTOM_LEFT))
                            .into(pic2)
                        Glide.with(context)
                            .load(data.urls[3])
                            .transform(CenterCrop(),
                                RoundedCornersTransformation(SizeUtils.dp2px(5f),
                                    0,
                                    RoundedCornersTransformation.CornerType.BOTTOM_RIGHT))
                            .into(pic3)

                    }
                }
            }
        }
    }
}