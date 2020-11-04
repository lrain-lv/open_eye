package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.TopicItem
import com.app.eye.widgets.transformations.RoundedCornersTransformation
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder


class TopicListAdapter(dataList: MutableList<TopicItem>) :
    BaseQuickAdapter<TopicItem, BaseViewHolder>(
        data = dataList, layoutResId = R
            .layout.layout_topic_list_item
    ), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: TopicItem) {
        val ivIcon = holder.getView<ImageView>(R.id.iv_icon)
        Glide.with(context)
            .load(item.data.imageUrl)
            .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
            .into(ivIcon)
        holder.setText(R.id.tv_title, item.data.title)
            .setGone(R.id.iv_fire, !item.data.showHotSign)
            .setText(R.id.tv_count, "${item.data.viewCount}人浏览/${item.data.joinCount}人参与")
    }
}