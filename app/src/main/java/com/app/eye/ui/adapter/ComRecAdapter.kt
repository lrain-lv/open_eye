package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.rx.loadImageCircle
import com.app.eye.rx.loadImageRound
import com.app.eye.ui.mvp.model.entity.ComItem
import com.blankj.utilcode.util.ScreenUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder


class ComRecAdapter(dataList: MutableList<ComItem>) : BaseQuickAdapter<ComItem, BaseViewHolder>(
    data = dataList,
    layoutResId = R.layout.layout_com_rec_item
), LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ComItem) {
        item ?: return
        holder.setText(R.id.tv_dec, item.data.content.data.description)
            .setText(R.id.tv_name, item.data.header.issuerName)
            .setGone(R.id.iv_video, !item.data.content.type.contains("video"))
            .setGone(
                R.id.iv_muti, if (item.data.content.data.urls.isNullOrEmpty()) true else
                    item.data.content.data.urls.size <= 1
            )
            .setGone(R.id.tv_choose,
                !(holder.adapterPosition == 1 || holder.adapterPosition == 2 || holder.adapterPosition == 3))
            .setGone(R.id.tv_dec, item.data.content.data.description.isNullOrEmpty())
            .setText(R.id.tv_count, item.data.content.data.consumption.collectionCount.toString())
        val ivHeader = holder.getView<ImageView>(R.id.iv_header)
        ivHeader.loadImageCircle(context, item.data.header.icon, 30f)
        val ivFeed = holder.getView<ImageView>(R.id.iv_feed)
        val width = item.data.content.data.width
        val height = item.data.content.data.height
        val layoutParams = ivFeed.layoutParams
        val ratio = (height.toFloat() / width.toFloat()).coerceAtMost(1.5f)
        layoutParams.height =
            (ScreenUtils.getScreenWidth() / 2 * (ratio)).toInt()
        ivFeed.loadImageRound(context, item.data.content.data.cover.feed, isAnim = true)
    }
}