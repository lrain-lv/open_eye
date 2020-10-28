package com.app.eye.ui.adapter

import android.text.TextUtils
import android.widget.ImageView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.TagVideoItem
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.TimeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class TagVideoAdapter(data: MutableList<TagVideoItem>) :
    BaseMultiItemQuickAdapter<TagVideoItem, BaseViewHolder>(), LoadMoreModule {

    init {
        addItemType(TagVideoItem.TYPE_NONE, R.layout.layout_none)
        addItemType(TagVideoItem.TYPE_VIDEO, R.layout.layout_tab_video_item_video)
        addItemType(TagVideoItem.TYPE_PIC2, R.layout.layout_tab_video_item_pic2)
        addItemType(TagVideoItem.TYPE_PIC3, R.layout.layout_tab_video_item_pic3)
        addItemType(TagVideoItem.TYPE_PIC4, R.layout.layout_tab_video_item_pic4)
    }

    override fun convert(holder: BaseViewHolder, item: TagVideoItem) {
        if (item.itemType == TagVideoItem.TYPE_NONE) return
        val ivHeader = holder.getView<ImageView>(R.id.iv_header)
        val data = item.data.content.data
        val header = item.data.header
        Glide.with(context)
            .load(header.icon)
            .circleCrop()
            .override(SizeUtils.dp2px(45f), SizeUtils.dp2px(45f))
            .into(ivHeader)
        holder.setText(R.id.tv_name, header.issuerName)
            .setText(R.id.tv_date,
                if (data.area.isNullOrEmpty()) "${TimeUtils.getFriendlyTimeSpanByNow(item.data.content.data.createTime)}发布："
                else "${TimeUtils.getFriendlyTimeSpanByNow(item.data.content.data.createTime)}在${data.area}发布：")
            .setText(R.id.tv_tag, data.tags[0].name)
            .setText(R.id.tv_content, data.description)
            .setGone(R.id.tv_content,data.description.isNullOrEmpty())
            .setText(R.id.tv_like_count, data.consumption.collectionCount.toString())
            .setText(R.id.tv_reply_count, data.consumption.replyCount.toString())

        when (item.itemType) {
            TagVideoItem.TYPE_VIDEO -> {
                holder.setGone(R.id.iv_play, !TextUtils.equals("video", item.data.content.type))
                val ivVideo = holder.getView<ImageView>(R.id.iv_video)
                Glide.with(context)
                    .load(data.cover.feed)
                    .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
                    .into(ivVideo)
            }
            TagVideoItem.TYPE_PIC2 -> {
                val pic1 = holder.getView<ImageView>(R.id.iv_pic1)
                val pic2 = holder.getView<ImageView>(R.id.iv_pic2)
                Glide.with(context)
                    .load(data.urls[0])
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
                            RoundedCornersTransformation.CornerType.RIGHT))
                    .into(pic2)
            }
            TagVideoItem.TYPE_PIC3 -> {
                val pic1 = holder.getView<ImageView>(R.id.iv_pic1)
                val pic2 = holder.getView<ImageView>(R.id.iv_pic2)
                val pic3 = holder.getView<ImageView>(R.id.iv_pic3)
                Glide.with(context)
                    .load(data.urls[0])
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
                holder.setGone(R.id.tv_more, data.urls.size == 4)
                    .setText(R.id.tv_more, if (data.urls.size > 4) "+${data.urls.size - 4}" else "")
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