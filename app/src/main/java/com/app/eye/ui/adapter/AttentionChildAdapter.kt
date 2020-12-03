package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.rx.loadImageCircle
import com.app.eye.ui.entity.AttUser
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class AttentionChildAdapter(data: MutableList<AttUser>) : BaseQuickAdapter<AttUser, BaseViewHolder>(
    data = data,
    layoutResId = R.layout.layout_attention_follow_child_item) {
    override fun convert(holder: BaseViewHolder, item: AttUser) {
        val img = holder.getView<ImageView>(R.id.iv_img)
        img.loadImageCircle(item.avatar,40f)
    }
}