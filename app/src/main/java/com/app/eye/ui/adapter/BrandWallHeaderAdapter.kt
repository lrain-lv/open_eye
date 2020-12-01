package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.rx.loadImageCommon
import com.app.eye.ui.entity.BrandMetro
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class BrandWallHeaderAdapter(data: MutableList<BrandMetro>) :
    BaseQuickAdapter<BrandMetro, BaseViewHolder>(
        data = data,
        layoutResId = com.app.eye.R.layout.layout_brand_wall_header_item
    ) {
    override fun convert(holder: BaseViewHolder, item: BrandMetro) {
        val img = holder.getView<ImageView>(com.app.eye.R.id.iv_img)
        img.loadImageCommon(context, item.metro_data.cover.url)
        holder.setText(R.id.tv_title, item.metro_data.title)
    }
}