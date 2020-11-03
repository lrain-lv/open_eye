package com.app.eye.ui.adapter

import com.app.eye.R
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.github.chrisbanes.photoview.PhotoView

class GalleryImgChildAdapter(data: MutableList<String>) :
    BaseQuickAdapter<String, BaseViewHolder>(data = data,
        layoutResId = R.layout.layout_gallery_child_img) {
    override fun convert(holder: BaseViewHolder, item: String) {
        var photoView = holder.getView<PhotoView>(R.id.iv_img)
        Glide.with(context)
            .load(item)
            .into(photoView)
    }

}