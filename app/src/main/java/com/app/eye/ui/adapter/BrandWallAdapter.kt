package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.rx.loadImageRound
import com.app.eye.ui.entity.BrandMetro
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class BrandWallAdapter(data: MutableList<BrandMetro>) :
    BaseQuickAdapter<BrandMetro, BaseViewHolder>(
        data = data, layoutResId =
        R.layout.layout_brand_wall_item
    ),LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: BrandMetro) {
        val img = holder.getView<ImageView>(R.id.iv_img)
        img.loadImageRound( item.metro_data.cover.url,)
        holder.setText(R.id.tv_content, item.metro_data.title)
            .setText(R.id.tv_duration, item.metro_data.duration.text)
            .setText(R.id.tv_hot, item.metro_data.hot_value.toString())
    }
}