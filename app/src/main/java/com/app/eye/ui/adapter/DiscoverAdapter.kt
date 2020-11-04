package com.app.eye.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.eye.R
import com.app.eye.rx.actionUrlToMap
import com.app.eye.ui.activity.TagVideoActivity
import com.app.eye.ui.activity.WebActivity
import com.app.eye.ui.mvp.model.entity.DataX
import com.app.eye.ui.mvp.model.entity.Item
import com.app.eye.ui.mvp.model.entity.Item.Companion.DISCOVER_AUTO_PLAY
import com.app.eye.ui.mvp.model.entity.Item.Companion.DISCOVER_BANNER
import com.app.eye.ui.mvp.model.entity.Item.Companion.DISCOVER_BRIEF_CARD
import com.app.eye.ui.mvp.model.entity.Item.Companion.DISCOVER_COLUMN_CARD_LIST
import com.app.eye.ui.mvp.model.entity.Item.Companion.DISCOVER_SQUARE_CARD
import com.app.eye.ui.mvp.model.entity.Item.Companion.DISCOVER_TEXT_CARD
import com.app.eye.ui.mvp.model.entity.Item.Companion.DISCOVER_VIDEO_SMALL_CARD
import com.app.eye.ui.mvp.model.entity.Item.Companion.NONE
import com.app.eye.ui.mvp.model.entity.ItemX
import com.app.eye.widgets.itemdecoration.LayoutMarginDecoration
import com.app.eye.widgets.transformations.RoundedCornersTransformation
import com.app.eye.widgets.videoplayer.EyeVideoPlayer
import com.app.eye.widgets.videoplayer.JzViewOutlineProvider
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.youth.banner.Banner

import kotlinx.android.synthetic.main.jz_layout_std.view.*
import java.util.*


class DiscoverAdapter(var datas: MutableList<Item>) :
    BaseMultiItemQuickAdapter<Item, BaseViewHolder>(datas), LoadMoreModule {

    lateinit var banner: Banner<ItemX, BannerItemAdapter>

    init {
        addItemType(DISCOVER_BANNER, R.layout.layout_discover_banner)
        addItemType(DISCOVER_SQUARE_CARD, R.layout.layout_discover_squard_card)
        addItemType(DISCOVER_COLUMN_CARD_LIST, R.layout.layout_discover_squard_card)
        addItemType(DISCOVER_TEXT_CARD, R.layout.layout_discover_text_card)
        addItemType(DISCOVER_VIDEO_SMALL_CARD, R.layout.layout_discover_video_card)
        addItemType(DISCOVER_BRIEF_CARD, R.layout.layout_discover_brief_card)
        addItemType(DISCOVER_AUTO_PLAY, R.layout.layout_discover_video)
        addItemType(NONE, R.layout.layout_none)
    }

    override fun convert(holder: BaseViewHolder, item: Item) {
        when (item.itemType) {
            DISCOVER_AUTO_PLAY -> {
                val detail = item.data.detail
                holder.setText(R.id.tv_title, detail.title)
                    .setText(R.id.tv_dec, detail.description)
                val ivHeader = holder.getView<ImageView>(R.id.iv_header)
                Glide.with(context)
                    .load(detail.icon)
                    .circleCrop()
                    .override(SizeUtils.dp2px(36f), SizeUtils.dp2px(36f))
                    .into(ivHeader)
                val player = holder.getView<EyeVideoPlayer>(R.id.player)
                player.start_layout.visibility = View.GONE
                player.outlineProvider = JzViewOutlineProvider(SizeUtils.dp2px(5f).toFloat())
                player.clipToOutline = true
                Glide.with(context)
                    .load(detail.imageUrl)
                    .centerCrop()
                    .into(player.thumbImageView)
                player.setUp(detail.url, "")
            }
            DISCOVER_BANNER -> {
                banner = holder.getView(R.id.banner)
                val adapter = BannerItemAdapter(mutableListOf())
                banner.apply {
                    setAdapter(adapter)
                    setBannerGalleryEffect(10, 6, 1f)
                    setScrollTime(1000)
                }
                banner.setOnBannerListener { _, position ->
                    val itemX = adapter.getData(position)
                    val actionUrl = itemX.data.actionUrl
                    if (actionUrl.contains("webview")) {
                        WebActivity.startWebActivity(
                            url = actionUrl.actionUrlToMap()["url"] ?: error(""),
                            title = actionUrl.actionUrlToMap()["title"] ?: error("")
                        )
                    } else {
                        ToastUtils.showShort(actionUrl)
                    }
                }
                if (TextUtils.equals("banner", item.type)) {
                    val itemX = ItemX(
                        DataX(
                            item.data.actionUrl!!, false, "", "", null,
                            item.data.id, item.data.image, null, mutableListOf(), false, "", "", ""
                        ), item.id, item.type
                    )
                    val itemList = mutableListOf<ItemX>()
                    itemList.add(itemX)
                    adapter.setDatas(itemList)
                } else {
                    adapter.setDatas(item.data.itemList)
                }
            }
            DISCOVER_SQUARE_CARD -> {
                val header = item.data.header!!
                holder.setText(R.id.tv_title, header.title)
                    .setText(R.id.tv_right_text, header.rightText)
                val squareRecycler = holder.getView<RecyclerView>(R.id.recycler)
                val adapter = SquareItemAdapter(item.data.itemList)
                squareRecycler.layoutManager =
                    GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
                if (squareRecycler.itemDecorationCount == 0) {
                    squareRecycler.addItemDecoration(LayoutMarginDecoration(2, SizeUtils.dp2px(5f)))
                }
                adapter.setOnItemClickListener { _, view, position ->
                    val item = adapter.getItem(position)
                    val actionUrl = item.data.actionUrl
                    if (actionUrl.contains("tag")) {
                        val s = actionUrl.substring(17)
                        val id = s.substring(0, s.indexOf("/"))
                        TagVideoActivity.startActivity(
                            id,
                            item.data.title,
                            item.data.image,
                            item.data.description ?: "")
                    }
                }
                squareRecycler.setHasFixedSize(true)
                squareRecycler.adapter = adapter
            }
            DISCOVER_COLUMN_CARD_LIST -> {
                val header = item.data.header!!
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
                val videoDuration = item.data.duration
                var time: String = ""
                if (videoDuration < 60) {
                    time = String.format(Locale.getDefault(), "00:%02d", videoDuration % 60)
                } else if (videoDuration < 3600) {
                    time = String.format(
                        Locale.getDefault(),
                        "%02d:%02d",
                        videoDuration / 60,
                        videoDuration % 60
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
                    .override(SizeUtils.dp2px(70f), SizeUtils.dp2px(70f))
                    .transform(CenterCrop(), RoundedCornersTransformation(SizeUtils.dp2px(5f), 0))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(img)
            }


        }
    }
}