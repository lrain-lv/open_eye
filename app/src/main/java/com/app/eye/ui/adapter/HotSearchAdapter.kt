package com.app.eye.ui.adapter

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.HotSearchEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.util.regex.Pattern

class HotSearchAdapter(mutableList: MutableList<HotSearchEntity>) :
    BaseQuickAdapter<HotSearchEntity, BaseViewHolder>(
        data = mutableList,
        layoutResId = R.layout.layout_hot_search_item
    ) {
    override fun convert(holder: BaseViewHolder, item: HotSearchEntity) {
        item ?: return
        if (item.isSearch) {
            val tvHot = holder.getView<TextView>(R.id.tv_hot)
            holder.setGone(R.id.tv_del, true)
                .setTextColor(R.id.tv_hot, context.resources.getColor(R.color.black))
                .setText(R.id.tv_hot, item.searchResult)
            tvHot.textSize = 14f
        } else {
            val tvHot = holder.getView<TextView>(R.id.tv_hot)
            holder.setText(R.id.tv_hot, item.keyword)
                .setTextColor(
                    R.id.tv_hot,
                    if (item.isKeyWord) context.resources.getColor(R.color.cancelColor) else context.resources.getColor(
                        R.color.black
                    )
                )
                .setGone(R.id.tv_del, !TextUtils.equals("搜索历史", item.keyword))
            tvHot.textSize = if (item.isKeyWord) 14f else 18f
        }

    }
}