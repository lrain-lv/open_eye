package com.app.eye.ui.adapter

import android.widget.ImageView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.ItemX
import com.blankj.utilcode.util.ScreenUtils
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class SquareCardAdapter(dataList: MutableList<ItemX>) : BaseQuickAdapter<ItemX, BaseViewHolder>(
    data = dataList, layoutResId =
    R.layout.layout_square_card_item
) {
    private val width by lazy { ScreenUtils.getAppScreenWidth() }

    override fun convert(holder: BaseViewHolder, item: ItemX) {
        val imgPic = holder.getView<ImageView>(R.id.iv_pic)
        Glide.with(context)
            .load(item.data.bgPicture)
            .into(imgPic)
        holder.setText(R.id.tv_title, item.data.title)
            .setText(R.id.tv_sub_title, item.data.subTitle)
    }
}