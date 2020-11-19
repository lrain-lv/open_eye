package com.app.eye.ui.adapter

import android.text.TextUtils
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.eye.R
import com.app.eye.rx.loadImageCircle
import com.app.eye.rx.loadImageWithTransform
import com.app.eye.ui.mvp.model.entity.ItemDaily
import com.app.eye.widgets.NoScrollLinearLayoutManager
import com.app.eye.widgets.transformations.RoundedCornersTransformation
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import java.util.*

class DailyAdapter(dataList: MutableList<ItemDaily>) :
    BaseMultiItemQuickAdapter<ItemDaily, BaseViewHolder>(dataList), LoadMoreModule {

    init {
        addItemType(ItemDaily.NONE, R.layout.layout_none)
        addItemType(ItemDaily.DAILY_TEXT, R.layout.layout_discover_text_card)
        addItemType(ItemDaily.DAILY_VIDEO, R.layout.layout_daily_video_card)
        addItemType(ItemDaily.DAILY_INFORMATION, R.layout.layout_daily_infomation_card)
    }

    override fun convert(holder: BaseViewHolder, item: ItemDaily) {
        when (item.itemType) {
            ItemDaily.DAILY_TEXT -> {
                holder.setText(R.id.tv_title, item.data.text)
                    .setGone(R.id.tv_right_text, TextUtils.isEmpty(item.data.rightText))
                    .setText(R.id.tv_right_text, item.data.rightText ?: "")
            }
            ItemDaily.DAILY_VIDEO -> {
                val data = item.data.content.data
                val videoDuration = data.duration
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
                holder.setText(R.id.tv_title, data.title)
                    .setText(R.id.tv_category, "${data.author.name} / #${data.category}")
                    .setText(R.id.tv_duration, time)
                val ivCover = holder.getView<ImageView>(R.id.iv_cover)
                ivCover.loadImageWithTransform(
                    context,
                    data.cover.feed,
                    RoundedCornersTransformation(
                        SizeUtils.dp2px(5f),
                        0,
                        RoundedCornersTransformation.CornerType.TOP
                    )
                )
                val ivHeader = holder.getView<ImageView>(R.id.iv_header)
                ivHeader.loadImageCircle(context, data.author.icon, 36f)
            }
            ItemDaily.DAILY_INFORMATION -> {
                val data = item.data
                val ivCover = holder.getView<ImageView>(R.id.iv_cover)
                val recycler = holder.getView<RecyclerView>(R.id.recycler)
                ivCover.loadImageWithTransform(
                    context,
                    data.backgroundImage,
                    RoundedCornersTransformation(
                        SizeUtils.dp2px(5f),
                        0,
                        RoundedCornersTransformation.CornerType.TOP
                    )
                )
                val adapter = DailyInfoAdapter(data.titleList)
                recycler.layoutManager =
                    NoScrollLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                recycler.adapter = adapter
            }
        }
    }
}