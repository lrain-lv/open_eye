package com.app.eye.ui.adapter

import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.Message
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class PushMessageAdapter(dataList: MutableList<Message>) :
    BaseQuickAdapter<Message, BaseViewHolder>(
        data = dataList,
        layoutResId = R.layout.layout_push_item
    ), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: Message) {
        item ?: return
        val date = TimeUtils.getFriendlyTimeSpanByNow(item.date)
        holder.setText(R.id.tv_title, item.title)
            .setText(R.id.tv_dec, item.content)
            .setText(R.id.tv_date, date)
    }
}