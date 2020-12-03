package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.rx.loadImageCircle
import com.app.eye.rx.loadImageRound
import com.app.eye.ui.entity.RankItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.util.*

class RankAdapter(data: MutableList<RankItem>) :
    BaseQuickAdapter<RankItem, BaseViewHolder>(data = data, layoutResId = R
        .layout.layout_rank_item), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: RankItem) {
        val header = holder.getView<ImageView>(R.id.iv_header)
        val img = holder.getView<ImageView>(R.id.iv_img)

        val videoDuration = item.data.content.data.duration
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
        holder.setText(R.id.tv_title, item.data.header.title)
            .setText(R.id.tv_dec, item.data.header.description)
            .setText(R.id.tv_duration, time)
        header.loadImageCircle( item.data.header.icon, 40f)
        img.loadImageRound( item.data.content.data.cover.feed)
    }
}