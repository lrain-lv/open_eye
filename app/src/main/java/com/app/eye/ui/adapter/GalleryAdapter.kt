package com.app.eye.ui.adapter

import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.app.eye.R
import com.app.eye.event.callback.OnItemClickCallback
import com.app.eye.ui.entity.ComItem
import com.blankj.utilcode.util.StringUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class GalleryAdapter(data: MutableList<ComItem>,var onItemCallback : OnItemClickCallback) :
    BaseQuickAdapter<ComItem, BaseViewHolder>(data = data, layoutResId =
    R.layout.layout_gallery_pic_item),LoadMoreModule {

    override fun convert(holder: BaseViewHolder, item: ComItem) {
        val viewPager = holder.getView<ViewPager2>(R.id.view_pager)
        val textview = holder.getView<TextView>(R.id.tv_count)
        val urls = mutableListOf<String>()
        val type = item.data.content.type
        if (StringUtils.equalsIgnoreCase("video", type)) {
            urls.add(item.data.content.data.cover.feed)
        } else {
            urls.addAll(item.data.content.data.urls!!)
            holder.setGone(R.id.tv_count, urls.size == 1)
                .setText(R.id.tv_count, "1/${urls.size}")
            val imgAdapter = GalleryImgChildAdapter(urls)
            imgAdapter.addChildClickViewIds(R.id.iv_img)
            imgAdapter.setOnItemChildClickListener { adapter, view, position ->
                onItemCallback.onClick()
            }
            viewPager.offscreenPageLimit = 1
            viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    holder.setText(R.id.tv_count, "${position + 1}/${urls.size}")
                }
            })
            viewPager.adapter = imgAdapter
        }
    }
}