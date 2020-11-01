package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.ReplyItem
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class TopicDetailAdapter(data: MutableList<ReplyItem>) :
    BaseMultiItemQuickAdapter<ReplyItem, BaseViewHolder>(data = data), LoadMoreModule {

    init {
        addItemType(ReplyItem.TYPE_NONE, R.layout.layout_none)
        addItemType(ReplyItem.TYPE_TEXT, R.layout.layout_discover_text_card)
        addItemType(ReplyItem.TYPE_REPLY, R.layout.layout_topic_detail_reply_item)
    }

    override fun convert(holder: BaseViewHolder, item: ReplyItem) {
        when (item.itemType) {
            ReplyItem.TYPE_TEXT -> {
                holder.setGone(R.id.tv_right_text, item.data.actionUrl.isNullOrEmpty())
                    .setText(
                        R.id.tv_right_text,
                        if (!item.data.actionUrl.isNullOrEmpty()) "查看全部" else ""
                    )
                    .setText(R.id.tv_title, item.data.text)
            }

            ReplyItem.TYPE_REPLY -> {
                val data = item.data

                holder.setGone(R.id.tv_reply_name, data.parentReply == null)
                    .setGone(R.id.iv_main, data.imageUrl.isNullOrEmpty())
                    .setGone(R.id.layout_reply, data.parentReply == null)
                    .setGone(R.id.tv_get_converse, data.parentReply == null)
                    .setGone(
                        R.id.iv_reply,
                        data.parentReply == null || data.parentReply.imageUrl.isNullOrEmpty()
                    )
                    .setText(R.id.tv_name, data.user.nickname)
                    .setText(
                        R.id.tv_reply_name,
                        if (data.parentReply == null) "" else "回复 @${data.parentReply.user.nickname}"
                    )
                    .setText(R.id.tv_content, data.message)
                    .setText(
                        R.id.tv_name_reply,
                        if (data.parentReply == null) "" else data.parentReply.user.nickname
                    )
                    .setText(
                        R.id.tv_content_reply,
                        if (data.parentReply == null) "" else data.parentReply.message
                    )
                    .setText(
                        R.id.tv_time,
                        com.blankj.utilcode.util.TimeUtils.getFriendlyTimeSpanByNow(data.createTime)
                    )
                val headerBig = holder.getView<ImageView>(R.id.iv_header_big)
                Glide.with(context)
                    .load(data.user.avatar)
                    .circleCrop()
                    .override(SizeUtils.dp2px(36f), SizeUtils.dp2px(36f))
                    .into(headerBig)
                val ivMain = holder.getView<ImageView>(R.id.iv_main)
                if (!data.imageUrl.isNullOrEmpty()) {
                    Glide.with(context)
                        .load(data.imageUrl)
                        .transform(
                            CenterCrop(),
                            RoundedCornersTransformation(SizeUtils.dp2px(5f), 0)
                        )
                        .into(ivMain)
                }
                if (data.parentReply != null) {
                    val headerSm = holder.getView<ImageView>(R.id.iv_header_sm)
                    val ivReply = holder.getView<ImageView>(R.id.iv_reply)
                    Glide.with(context)
                        .load(data.parentReply.user.avatar)
                        .circleCrop()
                        .override(SizeUtils.dp2px(26f), SizeUtils.dp2px(26f))
                        .into(headerSm)
                    holder.setText(R.id.tv_name_reply, data.parentReply.user.nickname)
                        .setText(R.id.tv_content_reply, data.parentReply.message)
                    if (!data.parentReply.imageUrl.isNullOrEmpty()) {
                        Glide.with(context)
                            .load(data.parentReply.imageUrl)
                            .transform(
                                CenterCrop(),
                                RoundedCornersTransformation(SizeUtils.dp2px(5f), 0)
                            )
                            .into(ivReply)
                    }
                }

            }
        }
    }
}