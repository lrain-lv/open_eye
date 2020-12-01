package com.app.eye.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.app.eye.R
import com.app.eye.rx.loadImageCircle
import com.app.eye.ui.entity.RecItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class RecFriendAdapter(data: MutableList<RecItem>) :
    BaseQuickAdapter<RecItem, BaseViewHolder>(data = data,
        layoutResId = R.layout.layout_rec_friend_item), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: RecItem) {
        val img = holder.getView<ImageView>(R.id.iv_icon)
        val tvAttention = holder.getView<TextView>(R.id.tv_attention)
        if (item.data.follow != null) {
            tvAttention.isEnabled = item.data.follow.followed
            tvAttention.text = if (item.data.follow.followed) "已关注" else "+关注"
        } else {
            tvAttention.isEnabled = false
            tvAttention.text = "+关注"
        }
        holder.setText(R.id.tv_brief_title, item.data.title)
            .setText(R.id.tv_dec, item.data.description)
        img.loadImageCircle(context, item.data.icon, 40f)
    }
}