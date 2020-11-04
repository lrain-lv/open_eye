package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.rx.loadImageRound
import com.app.eye.ui.mvp.model.entity.SpecialTopicItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder


class SpecialTopicAdapter(data: MutableList<SpecialTopicItem>) :
    BaseQuickAdapter<SpecialTopicItem, BaseViewHolder>(data = data, layoutResId =
    R.layout.layout_special_topic_item), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: SpecialTopicItem) {
        val img = holder.getView<ImageView>(R.id.iv_img)
        img.loadImageRound(context,item.data.image)
    }
}