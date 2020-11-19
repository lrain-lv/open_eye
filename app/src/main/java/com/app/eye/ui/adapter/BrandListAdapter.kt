package com.app.eye.ui.adapter

import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.BrandListItemX
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class BrandListAdapter(data: MutableList<BrandListItemX>) :
    BaseMultiItemQuickAdapter<BrandListItemX, BaseViewHolder>(
        data = data
    ) {

    init {
        addItemType(0, R.layout.layout_brand_list_item_index)
        addItemType(1, R.layout.layout_brand_list_item)
    }

    override fun convert(holder: BaseViewHolder, item: BrandListItemX) {
        when (item.itemType) {
            0 -> {
                holder.setText(R.id.tv_index, item.name)
            }
            1 -> {
                holder.setText(R.id.tv_name, item.name)
            }
        }
    }
}