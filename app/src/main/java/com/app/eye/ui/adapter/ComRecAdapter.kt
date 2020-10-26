package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.ComItem
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.orhanobut.logger.Logger
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class ComRecAdapter(dataList: MutableList<ComItem>) : BaseQuickAdapter<ComItem, BaseViewHolder>(
    data = dataList,
    layoutResId = R.layout.layout_com_rec_item
),LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ComItem) {
        item ?: return
        holder.setText(R.id.tv_dec, item.data.content.data.description)
            .setText(R.id.tv_name, item.data.header.issuerName)
            .setText(R.id.tv_count, item.data.content.data.consumption.collectionCount.toString())
        val ivHeader = holder.getView<ImageView>(R.id.iv_header)
        Glide.with(context)
            .load(item.data.header.icon)
            .circleCrop()
            .override(SizeUtils.dp2px(30f), SizeUtils.dp2px(30f))
            .into(ivHeader)
        val ivFeed = holder.getView<ImageView>(R.id.iv_feed)
        val width = item.data.content.data.width
        val height = item.data.content.data.height
        val layoutParams = ivFeed.layoutParams
        val ratio = (height.toFloat() / width.toFloat()).coerceAtMost(1.5f)
        layoutParams.height =
            (ScreenUtils.getScreenWidth() / 2 * (ratio)).toInt()
        ivFeed.layoutParams = layoutParams
        Glide.with(context)
            .load(item.data.content.data.cover.feed)
            .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
            .into(ivFeed)
    }
}