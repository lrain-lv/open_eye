package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.rx.loadImageRound
import com.app.eye.ui.entity.VrItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.util.*

class VideoDetailHeaderAdapter(data: MutableList<VrItem>) :
    BaseQuickAdapter<VrItem, BaseViewHolder>(data = data,
        layoutResId = R.layout.layout_discover_video_card) {

    var isShow: Boolean = false

    override fun convert(holder: BaseViewHolder, item: VrItem) {
        val videoDuration = item.data.duration
        var time: String = ""
        if (videoDuration < 60) {
            time = String.format(Locale.getDefault(), "00:%02d", videoDuration % 60)
        } else if (videoDuration < 3600) {
            time = String.format(
                Locale.getDefault(),
                "%02d:%02d",
                videoDuration / 60,
                videoDuration % 60
            )
        }
        val img = holder.getView<ImageView>(R.id.iv_pic)
        holder.setText(R.id.tv_video_title, item.data.title)
            .setText(R.id.tv_category, "${item.data.category} / ${item.data.author.name}")
            .setText(R.id.tv_duration, time)
        img.loadImageRound(context, item.data.cover.feed)
    }
}