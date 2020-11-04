package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.rx.loadImageRound
import com.app.eye.ui.mvp.model.entity.TopicItem
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
        ivIcon.loadImageRound(context,item.data.imageUrl)
        holder.setText(R.id.tv_title, item.data.title)
            .setGone(R.id.iv_fire, !item.data.showHotSign)
            .setText(R.id.tv_count, "${item.data.viewCount}人浏览/${item.data.joinCount}人参与")
    }
}