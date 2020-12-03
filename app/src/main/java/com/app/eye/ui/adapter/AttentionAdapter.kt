package com.app.eye.ui.adapter

import android.text.TextUtils
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.eye.R
import com.app.eye.rx.dp2px
import com.app.eye.rx.loadImageCircle
import com.app.eye.rx.loadImageRound
import com.app.eye.rx.loadImageWithTransform
import com.app.eye.ui.entity.AttItem
import com.app.eye.ui.entity.TagVideoItem
import com.app.eye.widgets.transformations.RoundedCornersTransformation
import com.blankj.utilcode.util.SizeUtils
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder


class AttentionAdapter(data: MutableList<AttItem>) :
    BaseMultiItemQuickAdapter<AttItem, BaseViewHolder>(data), LoadMoreModule {
    init {
        addItemType(AttItem.TYPE_NONE, R.layout.layout_none)
        addItemType(AttItem.TYPE_VIDEO, R.layout.layout_tab_video_item_video)
        addItemType(AttItem.TYPE_PIC2, R.layout.layout_tab_video_item_pic2)
        addItemType(AttItem.TYPE_PIC3, R.layout.layout_tab_video_item_pic3)
        addItemType(AttItem.TYPE_PIC4, R.layout.layout_tab_video_item_pic4)
        addItemType(AttItem.TYPE_USER, R.layout.layout_attention_follow_item)
    }

    override fun convert(holder: BaseViewHolder, item: AttItem) {
        if (item.itemType == TagVideoItem.TYPE_NONE) return
        if (item.itemType == AttItem.TYPE_USER) {
            val recyclerChild = holder.getView<RecyclerView>(R.id.recycler_child)
            recyclerChild.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            val adapter = AttentionChildAdapter(item.data.userList)
            recyclerChild.adapter = adapter
        } else {
            val ivHeader = holder.getView<ImageView>(R.id.iv_header)
            val data = item.data.content!!.data
            val header = item.data.header
            ivHeader.loadImageCircle(header.icon, 40f)
            holder.setText(R.id.tv_name, header.issuerName)
                .setText(
                    R.id.tv_date,
                    if (data.area.isNullOrEmpty()) "${TimeUtils.getFriendlyTimeSpanByNow(data.createTime)}发布："
                    else "${TimeUtils.getFriendlyTimeSpanByNow(data.createTime)}在${data.area}发布："
                )
                .setGone(R.id.tv_tag, true)
                .setText(R.id.tv_content, data.description)
                .setGone(R.id.tv_content, data.description.isNullOrEmpty())
                .setText(R.id.tv_like_count, data.consumption.collectionCount.toString())
                .setText(R.id.tv_reply_count, data.consumption.replyCount.toString())

            when (item.itemType) {
                TagVideoItem.TYPE_VIDEO -> {
                    holder.setGone(R.id.iv_play, !TextUtils.equals("video", item.data.content.type))
                    val ivVideo = holder.getView<ImageView>(R.id.iv_video)
                    ivVideo.loadImageRound(data.cover.feed, 5f)
                }
                TagVideoItem.TYPE_PIC2 -> {
                    val pic1 = holder.getView<ImageView>(R.id.iv_pic1)
                    val pic2 = holder.getView<ImageView>(R.id.iv_pic2)

                    pic1.loadImageWithTransform(
                        data.urls[0],
                        coil.transform.RoundedCornersTransformation(
                            topLeft = 5f.dp2px(),
                            bottomLeft = 5f.dp2px()
                        )
                    )
                    pic2.loadImageWithTransform(
                        data.urls[1], coil.transform.RoundedCornersTransformation(
                            topRight = 5f.dp2px(),
                            bottomRight = 5f.dp2px()
                        )
                    )
                }
                TagVideoItem.TYPE_PIC3 -> {
                    val pic1 = holder.getView<ImageView>(R.id.iv_pic1)
                    val pic2 = holder.getView<ImageView>(R.id.iv_pic2)
                    val pic3 = holder.getView<ImageView>(R.id.iv_pic3)

                    pic1.loadImageWithTransform(
                        data.urls[0], coil.transform.RoundedCornersTransformation(
                            topLeft = 5f.dp2px(),
                            bottomLeft = 5f.dp2px()
                        )
                    )
                    pic2.loadImageWithTransform(
                        data.urls[1], coil.transform.RoundedCornersTransformation(
                            topRight = 5f.dp2px(),
                        )
                    )
                    pic3.loadImageWithTransform(
                        data.urls[2], coil.transform.RoundedCornersTransformation(
                            bottomRight = 5f.dp2px(),
                        )
                    )
                }
                TagVideoItem.TYPE_PIC4 -> {
                    holder.setGone(R.id.tv_more, data.urls.size == 4)
                        .setText(
                            R.id.tv_more,
                            if (data.urls.size > 4) "+${data.urls.size - 4}" else ""
                        )
                    val pic1 = holder.getView<ImageView>(R.id.iv_pic1)
                    val pic0 = holder.getView<ImageView>(R.id.iv_pic0)
                    val pic2 = holder.getView<ImageView>(R.id.iv_pic2)
                    val pic3 = holder.getView<ImageView>(R.id.iv_pic3)

                    pic0.loadImageWithTransform(
                        data.urls[0], coil.transform.RoundedCornersTransformation(
                            topLeft = 5f.dp2px(),
                        )
                    )
                    pic1.loadImageWithTransform(
                        data.urls[1], coil.transform.RoundedCornersTransformation(
                            topRight = 5f.dp2px(),
                        )
                    )
                    pic2.loadImageWithTransform(
                        data.urls[2], coil.transform.RoundedCornersTransformation(
                            bottomLeft = 5f.dp2px(),
                        )
                    )

                    pic3.loadImageWithTransform(
                        data.urls[3], coil.transform.RoundedCornersTransformation(
                            bottomRight = 5f.dp2px(),
                        )
                    )
                }
            }
        }
    }
}