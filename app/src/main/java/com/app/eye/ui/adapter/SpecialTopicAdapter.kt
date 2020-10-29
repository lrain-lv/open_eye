package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.SpecialTopicItem
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

class SpecialTopicAdapter(data: MutableList<SpecialTopicItem>) :
    BaseQuickAdapter<SpecialTopicItem, BaseViewHolder>(data = data, layoutResId =
    R.layout.layout_special_topic_item), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: SpecialTopicItem) {
        val img = holder.getView<ImageView>(R.id.iv_img)

        Glide.with(context)
            .load(item.data.image)
            .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
            .into(img)
    }
}