package com.app.eye.ui.adapter

import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.app.eye.R
import com.app.eye.databinding.LayoutTopicReplyDarkItemBinding
import com.app.eye.rx.loadImageCircle
import com.app.eye.rx.loadImageRound
import com.app.eye.ui.entity.ReplyItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder


class TopicReplyAdapter(data: MutableList<ReplyItem>) :
    BaseQuickAdapter<ReplyItem, BaseDataBindingHolder<LayoutTopicReplyDarkItemBinding>>(
        data = data,
        layoutResId = R.layout.layout_topic_reply_dark_item
    ), LoadMoreModule {

    override fun convert(
        holder: BaseDataBindingHolder<LayoutTopicReplyDarkItemBinding>,
        item: ReplyItem
    ) {
        val data = item.data

        val binding = holder.dataBinding
        binding?.data = data
        binding?.executePendingBindings()
    }
}