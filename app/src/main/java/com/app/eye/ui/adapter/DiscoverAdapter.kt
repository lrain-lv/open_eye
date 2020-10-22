package com.app.eye.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.eye.R
import com.app.eye.ui.mvp.model.entity.Item
import com.app.eye.ui.mvp.model.entity.Item.Companion.DISCOVER_BANNER
import com.app.eye.ui.mvp.model.entity.Item.Companion.DISCOVER_BRIEF_CARD
import com.app.eye.ui.mvp.model.entity.Item.Companion.DISCOVER_COLUMN_CARD_LIST
import com.app.eye.ui.mvp.model.entity.Item.Companion.DISCOVER_SQUARE_CARD
import com.app.eye.ui.mvp.model.entity.Item.Companion.DISCOVER_TEXT_CARD
import com.app.eye.ui.mvp.model.entity.Item.Companion.DISCOVER_VIDEO_SMALL_CARD
import com.app.eye.ui.mvp.model.entity.Item.Companion.NONE
import com.app.eye.ui.mvp.model.entity.ItemX
import com.blankj.utilcode.util.SizeUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.thekhaeng.recyclerviewmargin.LayoutMarginDecoration
import com.youth.banner.Banner
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.util.*


class DiscoverAdapter(var datas: MutableList<Item>) :
    BaseMultiItemQuickAdapter<Item, BaseViewHolder>(datas) {

    lateinit var banner: Banner<ItemX, BannerItemAdapter>

    init {
        addItemType(DISCOVER_BANNER, R.layout.layout_discover_banner)
        addItemType(DISCOVER_SQUARE_CARD, R.layout.layout_discover_squard_card)
        addItemType(DISCOVER_COLUMN_CARD_LIST, R.layout.layout_discover_squard_card)
        addItemType(DISCOVER_TEXT_CARD, R.layout.layout_discover_text_card)
        addItemType(DISCOVER_VIDEO_SMALL_CARD, R.layout.layout_discover_video_card)
        addItemType(DISCOVER_BRIEF_CARD, R.layout.layout_discover_brief_card)
        addItemType(NONE, R.layout.layout_none)
    }

    override fun convert(holder: BaseViewHolder, item: Item) {
        when (item.itemType) {
            DISCOVER_BANNER -> {
                banner = holder.getView(R.id.banner)
                banner.adapter = BannerItemAdapter(item.data.itemList)
                banner.apply {
                    setBannerGalleryEffect(10, 6, 1f)
                    setScrollTime(1000)
                }
            }
            DISCOVER_SQUARE_CARD -> {
                val header = item.data.header
                holder.setText(R.id.tv_title, header.title)
                    .setText(R.id.tv_right_text, header.rightText)
                val squareRecycler = holder.getView<RecyclerView>(R.id.recycler)
                val adapter = SquareItemAdapter(item.data.itemList)
                squareRecycler.layoutManager =
                    GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
                if (squareRecycler.itemDecorationCount == 0) {
                    squareRecycler.addItemDecoration(LayoutMarginDecoration(2, SizeUtils.dp2px(5f)))
                }
                squareRecycler.setHasFixedSize(true)
                squareRecycler.adapter = adapter
            }
            DISCOVER_COLUMN_CARD_LIST -> {
                val header = item.data.header
                holder.setText(R.id.tv_title, header.title)
                    .setText(R.id.tv_right_text, header.rightText)
                val columnRecycler = holder.getView<RecyclerView>(R.id.recycler)
                val adapter = ColumnItemAdapter(item.data.itemList)
                columnRecycler.layoutManager =
                    GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
                if (columnRecycler.itemDecorationCount == 0) {
                    columnRecycler.addItemDecoration(LayoutMarginDecoration(2, SizeUtils.dp2px(5f)))
                }
                columnRecycler.setHasFixedSize(true)
                columnRecycler.adapter = adapter
            }
            DISCOVER_TEXT_CARD -> {
                holder.setText(R.id.tv_title, item.data.text)
                    .setText(R.id.tv_right_text, item.data.rightText)
            }
            DISCOVER_VIDEO_SMALL_CARD -> {
                var video_duration = item.data.duration
                var time: String = ""
                if (video_duration < 60) {
                    var time = String.format(Locale.getDefault(), "00:%02d", video_duration % 60)
                } else if (video_duration < 3600) {
                    time = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        video_duration / 60,
                        video_duration % 60
                    )
                }
                var img = holder.getView<ImageView>(R.id.iv_pic)
                holder.setText(R.id.tv_video_title, item.data.title)
                    .setText(R.id.tv_category, "${item.data.category} / ${item.data.author.name}")
                    .setText(R.id.tv_duration, time)
                Glide.with(context)
                    .load(item.data.cover.feed)
                    .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(img)
            }
            DISCOVER_BRIEF_CARD -> {
                var img = holder.getView<ImageView>(R.id.iv_icon)
                var tvAttention = holder.getView<TextView>(R.id.tv_attention)
                if (item.data.follow != null) {
                    tvAttention.isEnabled = item.data.follow.followed
                    tvAttention.text = if (item.data.follow.followed) "已关注" else "+关注"
                } else {
                    tvAttention.isEnabled = false
                    tvAttention.text = "+关注"
                }
                holder.setText(R.id.tv_brief_title, item.data.title)
                    .setText(R.id.tv_dec, item.data.description)
                Glide.with(context)
                    .load(item.data.icon)
                    .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(img)
            }


        }
    }
}