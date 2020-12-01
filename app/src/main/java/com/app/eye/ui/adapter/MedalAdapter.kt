package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.rx.loadImageCommon
import com.app.eye.ui.entity.BadgeItem
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class MedalAdapter(data: MutableList<BadgeItem>) : BaseQuickAdapter<BadgeItem, BaseViewHolder>(
    data = data,
    layoutResId = R.layout.layout_medal_item
),LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: BadgeItem) {
        val img = holder.getView<ImageView>(R.id.iv_img)
        val imgBg = holder.getView<ImageView>(R.id.iv_img_bg)
        img.loadImageCommon(context, item.data.medalIcon)
        imgBg.loadImageCommon(context, item.data.tagBgPicture)
        holder.setText(
            R.id.tv_time, if (item.data.receiveTime == null) "尚未获得" else
                "${TimeUtils.millis2String(item.data.receiveTime, "yyyy/MM/dd")} 获得"
        )
            .setGone(R.id.view, item.data.receiveTime != null)
    }
}