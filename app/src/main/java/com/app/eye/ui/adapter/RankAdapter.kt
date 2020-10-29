package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.RankItem
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class RankAdapter(data: MutableList<RankItem>) :
    BaseQuickAdapter<RankItem, BaseViewHolder>(data = data, layoutResId = R
        .layout.layout_rank_item), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: RankItem) {
        val header = holder.getView<ImageView>(R.id.iv_header)
        val img = holder.getView<ImageView>(R.id.iv_img)
        holder.setText(R.id.tv_title, item.data.header.title)
            .setText(R.id.tv_dec, item.data.header.description)

        Glide.with(context)
            .load(item.data.header.icon)
            .circleCrop()
            .override(SizeUtils.dp2px(40f), SizeUtils.dp2px(40f))
            .into(header)
        Glide.with(context)
            .load(item.data.content.data.cover.feed)
            .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
            .into(img)
    }
}