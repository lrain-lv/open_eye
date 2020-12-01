package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.rx.loadImageRound
import com.app.eye.ui.entity.TabItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder


class TopicChildAdapter(dataList: MutableList<TabItem>) : BaseQuickAdapter<TabItem, BaseViewHolder>(
    data = dataList, layoutResId = R
        .layout.layout_topic_child_item
), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: TabItem) {
        val ivIcon = holder.getView<ImageView>(R.id.iv_icon)
        ivIcon.loadImageRound(context,item.data.icon)
        holder.setText(R.id.tv_title, item.data.title)
            .setText(R.id.tv_dec, item.data.description)
    }
}