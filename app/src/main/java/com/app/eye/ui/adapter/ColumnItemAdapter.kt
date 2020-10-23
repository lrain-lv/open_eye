package com.app.eye.ui.adapter

import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.ItemX
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class ColumnItemAdapter(var dataList: MutableList<ItemX>) : BaseQuickAdapter<ItemX, BaseViewHolder>(
    data = dataList,
    layoutResId = R.layout.layout_discover_column_item
) {

    override fun convert(holder: BaseViewHolder, item: ItemX) {
        item ?: return
        var imageView = holder.getView<ImageView>(R.id.iv_image)
        var layout = holder.getView<ConstraintLayout>(R.id.container)
        val layoutParams = layout.layoutParams
        layoutParams.width = (ScreenUtils.getScreenWidth() - SizeUtils.dp2px(40f)) / 2
        holder.setText(R.id.tv_title, item.data.title)
        Glide.with(context)
            .load(item.data.image)
            .override(layoutParams.width, SizeUtils.dp2px(100f))
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
            .into(imageView)
    }
}