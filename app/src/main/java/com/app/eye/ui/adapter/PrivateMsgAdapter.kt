package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.rx.loadImageCircle
import com.app.eye.ui.entity.MsgItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class PrivateMsgAdapter(data: MutableList<MsgItem>) :
    BaseQuickAdapter<MsgItem, BaseViewHolder>(data = data,
        layoutResId = R.layout.layout_private_msg_item), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: MsgItem) {
        val img = holder.getView<ImageView>(R.id.iv_icon)
        img.loadImageCircle( item.data.user.avatar, 35f)
        holder.setText(R.id.tv_name, item.data.user.nickname)
            .setText(R.id.tv_time,
                com.blankj.utilcode.util.TimeUtils.getFriendlyTimeSpanByNow(item.data.lastTime))
            .setText(R.id.tv_content, item.data.content)
    }
}