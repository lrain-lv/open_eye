package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.rx.loadImageCommon
import com.app.eye.ui.entity.CategoryItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class CategoryAdapter(data: MutableList<CategoryItem>) :
    BaseQuickAdapter<CategoryItem, BaseViewHolder>(data = data, layoutResId =
    R.layout.layout_category_item), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: CategoryItem) {
        val img = holder.getView<ImageView>(R.id.iv_img)
        img.loadImageCommon(context,item.data.image)
    }
}