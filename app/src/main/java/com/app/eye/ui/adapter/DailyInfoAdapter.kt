package com.app.eye.ui.adapter

import com.app.eye.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class DailyInfoAdapter(string: MutableList<String>) : BaseQuickAdapter<String, BaseViewHolder>(
    data = string, layoutResId =
    R.layout.layout_daily_info_item
) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.setText(R.id.tv_info, item)
    }
}