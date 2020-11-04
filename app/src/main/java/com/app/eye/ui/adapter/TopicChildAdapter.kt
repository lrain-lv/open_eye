package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.TabItem
import com.app.eye.widgets.transformations.RoundedCornersTransformation
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder


class TopicChildAdapter(dataList: MutableList<TabItem>) : BaseQuickAdapter<TabItem, BaseViewHolder>(
    data = dataList, layoutResId = R
        .layout.layout_topic_child_item
), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: TabItem) {
        val ivIcon = holder.getView<ImageView>(R.id.iv_icon)
        Glide.with(context)
            .load(item.data.icon)
            .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
            .into(ivIcon)
        holder.setText(R.id.tv_title, item.data.title)
            .setText(R.id.tv_dec, item.data.description)
    }
}