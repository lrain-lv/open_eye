package com.app.eye.ui.adapter

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.eye.R
import com.app.eye.rx.loadImageRound
import com.app.eye.ui.entity.ItemX
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder


class ColumnItemAdapter(var dataList: MutableList<ItemX>) : BaseQuickAdapter<ItemX, BaseViewHolder>(
    data = dataList,
    layoutResId = R.layout.layout_discover_column_item
) {

    override fun convert(holder: BaseViewHolder, item: ItemX) {
        item ?: return
        val imageView = holder.getView<ImageView>(R.id.iv_image)
        val layout = holder.getView<ConstraintLayout>(R.id.container)
        val layoutParams = layout.layoutParams
        layoutParams.width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(40f)) / 2
        holder.setText(R.id.tv_title, item.data.title)
        imageView.loadImageRound(context,item.data.image,5f)

    }
}