package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.rx.loadImageRoundWithSize
import com.app.eye.ui.entity.ItemX
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder


class SquareItemAdapter(var dataList: MutableList<ItemX>) : BaseQuickAdapter<ItemX, BaseViewHolder>(
    data = dataList,
    layoutResId = R.layout.layout_discover_square_item
) {

    override fun convert(holder: BaseViewHolder, item: ItemX) {
        item ?: return
        var imageView = holder.getView<ImageView>(R.id.iv_image)
        holder.setText(R.id.tv_title, item.data.title)
        imageView.loadImageRoundWithSize( item.data.image, 100f)
    }
}