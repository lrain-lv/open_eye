package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.CategoryItem
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class CategoryAdapter(data: MutableList<CategoryItem>) :
    BaseQuickAdapter<CategoryItem, BaseViewHolder>(data = data, layoutResId =
    R.layout.layout_category_item), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: CategoryItem) {
        val img = holder.getView<ImageView>(R.id.iv_img)
        Glide.with(context)
            .load(item.data.image)
            .into(img)
    }
}